package com.freephoenix888.savemylife.services

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Build
import android.os.CombinedVibration
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.provider.Settings
import android.provider.Telephony
import android.util.Log
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.*
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.*
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.freephoenix888.savemylife.MyLifecycleOwner
import com.freephoenix888.savemylife.R
import com.freephoenix888.savemylife.broadcastReceivers.*
import com.freephoenix888.savemylife.constants.MessageConstants
import com.freephoenix888.savemylife.constants.NotificationConstants
import com.freephoenix888.savemylife.constants.NotificationConstants.CHANNEL_ID
import com.freephoenix888.savemylife.domain.models.PhoneNumber
import com.freephoenix888.savemylife.domain.useCases.*
import com.freephoenix888.savemylife.ui.SaveMyLifeActivity
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import javax.inject.Inject


@AndroidEntryPoint
class MainService : LifecycleService() {

    companion object{
        private val TAG = MainService::class.java.simpleName
    }

    @Inject
    lateinit var getMessageSettingsFlowUseCase: GetMessageSettingsFlowUseCase

    @Inject
    lateinit var getIsMainServiceEnabledFlowUseCase: GetIsMainServiceEnabledFlowUseCase

    @Inject
    lateinit var getIsDangerModeEnabledFlowUseCase: GetIsDangerModeEnabledFlowUseCase

    @Inject
    lateinit var getPhoneNumberListFlowUseCase: GetPhoneNumberListFlowUseCase


    private var smsBroadcastReceiver: SmsBroadcastReceiver? = null
    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val isMessageCommandsEnabled = MutableStateFlow(false)
    private val messageTemplate = MutableStateFlow("")
    private val messageSendingInterval = MutableStateFlow(MessageConstants.DEFAULT_SENDING_INTERVAL)
    private val phoneNumberList = MutableStateFlow(emptyList<PhoneNumber>())
    private val powerButtonBroadcastReceiver = PowerButtonBroadcastReceiver()

    private val alarmManager: AlarmManager by lazy { this@MainService.getSystemService(Context.ALARM_SERVICE) as AlarmManager }
//    private var alarmIntent: PendingIntent? = null

    private val dangerModeBeforeStartTimerInSeconds = MutableStateFlow(5)

    private val dangerModeEnsuringView by lazy {
        ComposeView(this)
    }


    override fun onCreate() {
        startForegroundService()
        registerPowerButtonBroadcastReceiver()
        super.onCreate()
    }

    init {
        lifecycleScope.launchWhenCreated {
            withContext(Dispatchers.IO) {
                getIsDangerModeEnabledFlowUseCase().collect() { isDangerModeEnabled ->
                    if(isDangerModeEnabled) {
                        sendBroadcast(
                            Intent(applicationContext, DangerBroadcastReceiver::class.java)
                        )
                    } else {
                        sendBroadcast(
                            Intent(applicationContext, DangerCancellationBroadcastReceiver::class.java)
                        )
                    }
                }
            }
        }
        lifecycleScope.launchWhenCreated {
            withContext(Dispatchers.IO) {
                getIsMainServiceEnabledFlowUseCase().collect {
                    if (!it) {
                        this@MainService.stopService(
                            Intent(
                                this@MainService,
                                this@MainService::class.java
                            )
                        )
                    }
                }
            }

        }
        lifecycleScope.launchWhenCreated {
            withContext(Dispatchers.IO) {
                getPhoneNumberListFlowUseCase().collect {
                    phoneNumberList.value = it
                }
            }
        }
        lifecycleScope.launchWhenCreated {
            withContext(Dispatchers.IO) {
                getMessageSettingsFlowUseCase().collect {
                    messageSendingInterval.value = it.sendingInterval
                    isMessageCommandsEnabled.value = it.isMessageCommandsEnabled
                    messageTemplate.value = it.template

                    if(it.isMessageCommandsEnabled && smsBroadcastReceiver != null){
                        smsBroadcastReceiver = SmsBroadcastReceiver()
                        val filter = IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)
                        registerReceiver(smsBroadcastReceiver, filter, 2)
                    } else if (smsBroadcastReceiver != null) {
                        unregisterReceiver(smsBroadcastReceiver)
                    }
                }

            }
        }
        lifecycleScope.launchWhenCreated {
            withContext(Dispatchers.IO) {
                dangerModeBeforeStartTimerInSeconds.collect {
                    Log.d(TAG, "NEW TIMER VALUE: $it")
                }
            }
        }

    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand: ")
        super.onStartCommand(intent, flags, startId)
        return START_STICKY
    }

    override fun onDestroy() {
        unregisterReceiver(powerButtonBroadcastReceiver)
        if(smsBroadcastReceiver != null) {
            unregisterReceiver(smsBroadcastReceiver)
        }
        super.onDestroy()
    }

    private fun registerPowerButtonBroadcastReceiver() {
        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_SCREEN_ON)
            addAction(Intent.ACTION_SCREEN_OFF)
        }
        registerReceiver(powerButtonBroadcastReceiver, filter)
    }

    private fun startForegroundService() {
        val notification = getForegroundServiceNotification()
        startForeground(NotificationConstants.ID, notification)
    }

    private fun getForegroundServiceNotification(): Notification {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }
        val saveMyLifeActivityIntent = Intent(this, SaveMyLifeActivity::class.java)/*.apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }*/
        val saveMyLifeActivityPendingIntent = PendingIntent.getActivity(
            this, 0, saveMyLifeActivityIntent,
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
        )
        val notificationBuilder = NotificationCompat.Builder(this, NotificationConstants.CHANNEL_ID)
            .setAutoCancel(false)
            .setOngoing(true)
            .setSmallIcon(R.drawable.ic_stat_name)
            .setLargeIcon(
                BitmapFactory.decodeResource(applicationContext.resources, R.mipmap.ic_launcher)
            )
            .setContentTitle("SaveMyLife")
            .setContentText("SaveMyLife is active")
            .setContentIntent(saveMyLifeActivityPendingIntent)
        return notificationBuilder.build()
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.all_app_name)
            val descriptionText = getString(R.string.all_app_name)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}