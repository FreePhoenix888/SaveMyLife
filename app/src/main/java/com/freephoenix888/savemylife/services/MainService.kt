package com.freephoenix888.savemylife.services

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.net.toUri
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.freephoenix888.savemylife.R
import com.freephoenix888.savemylife.broadcastReceivers.DangerBroadcastReceiver
import com.freephoenix888.savemylife.broadcastReceivers.PowerButtonBroadcastReceiver
import com.freephoenix888.savemylife.broadcastReceivers.RestartBroadcastReceiver
import com.freephoenix888.savemylife.constants.*
import com.freephoenix888.savemylife.domain.models.PhoneNumber
import com.freephoenix888.savemylife.domain.useCases.*
import com.freephoenix888.savemylife.ui.SaveMyLifeActivity
import com.freephoenix888.savemylife.ui.SaveMyLifeScreenEnum
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


@AndroidEntryPoint
class MainService : LifecycleService() {

    companion object{
        private val TAG = MainService::class.java.simpleName
    }

    @Inject
    lateinit var getIsMainServiceEnabledFlowUseCase: GetIsMainServiceEnabledFlowUseCase

    @Inject
    lateinit var getIsDangerModeEnabledFlowUseCase: GetIsDangerModeEnabledFlowUseCase

    @Inject
    lateinit var getPhoneNumberListFlowUseCase: GetPhoneNumberListFlowUseCase

    @Inject
    lateinit var getMessageUseCase: GetMessageUseCase

    @Inject
    lateinit var getMessageSendingIntervalFlowUseCase: GetMessageSendingIntervalFlowUseCase

    private var isFirstStart = true
    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private var isDangerModeEnabled: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val messageSendingInterval = MutableStateFlow(MessageConstants.DEFAULT_SENDING_INTERVAL)
    private val phoneNumberList = MutableStateFlow(emptyList<PhoneNumber>())
    private val powerButtonBroadcastReceiver = PowerButtonBroadcastReceiver()

    private var alarmManager: AlarmManager? = null
    private var alarmIntent: PendingIntent? = null


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        registerPowerButtonBroadcastReceiver()
        if (intent == null) {
            return START_STICKY
        }

        when (intent.action) {
            ActionConstants.StartMainService -> {
                if (isFirstStart) {
                    startForegroundService()
                    isFirstStart = false
                }
            }
            else -> {}
        }
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                getIsMainServiceEnabledFlowUseCase().collect {
                    if (!it) {
                        applicationContext.stopService(
                            Intent(
                                applicationContext,
                                MainService::class.java
                            )
                        )
                    }
                }
            }
        }
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                getMessageSendingIntervalFlowUseCase().collect {
                    messageSendingInterval.value = it
                }
            }
        }
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                getPhoneNumberListFlowUseCase().collect {
                    phoneNumberList.value = it
                }
            }
        }
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                getIsDangerModeEnabledFlowUseCase().collect {
                    isDangerModeEnabled.value = it
                    alarmManager =
                        applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                    if (alarmIntent != null) {
                        alarmManager?.cancel(alarmIntent)
                    }
                    if (it) {
                        val dialogIntent =
                            Intent(applicationContext, DangerBroadcastReceiver::class.java)
                        alarmIntent = PendingIntent.getBroadcast(
                            applicationContext,
                            0,
                            dialogIntent,
                            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_CANCEL_CURRENT
                        )
                        alarmManager?.setRepeating(
                            AlarmManager.RTC_WAKEUP,
                            System.currentTimeMillis(),
                            messageSendingInterval.value.inWholeMilliseconds,
                            alarmIntent
                        )
                    }
                }
            }
        }

        return START_STICKY
    }

    override fun onDestroy() {
        sendBroadcast(Intent(applicationContext, RestartBroadcastReceiver::class.java))
        unregisterReceiver(powerButtonBroadcastReceiver)
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
        val notification = getNotification()
        startForeground(NotificationConstants.ID, notification)
    }

    private fun getNotification(): Notification {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }
        val dangerButtonPendingIntent = Intent(Intent.ACTION_VIEW, DeepLinks.dangerButton, applicationContext, SaveMyLifeActivity::class.java).let {
            TaskStackBuilder.create(applicationContext).addNextIntentWithParentStack(it).getPendingIntent(0, PendingIntent.FLAG_IMMUTABLE)
        }
        val notificationBuilder = NotificationCompat.Builder(this, NotificationConstants.CHANNEL_ID)
            .setAutoCancel(false)
            .setOngoing(true)
            .setSmallIcon(R.drawable.ic_stat_name)
            .setLargeIcon(
                BitmapFactory.decodeResource(applicationContext.resources, R.mipmap.ic_launcher)
            )
            .setContentTitle("SaveMyLife")
            .setContentText("SaveMyLife is active")
            .setContentIntent(getDangerButtonActivityPendingIntent())
            .setContentIntent(dangerButtonPendingIntent)
        return notificationBuilder.build()
    }

    private fun getDangerButtonActivityPendingIntent(): PendingIntent {
        val dangerButtonIntent = Intent(
            Intent.ACTION_VIEW,
            "${Constants.APP_URI}/screen/${SaveMyLifeScreenEnum.DangerButton.name}".toUri(),
            applicationContext,
            SaveMyLifeActivity::class.java
        )
        val flags: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }
        val pendingIntent = TaskStackBuilder.create(applicationContext).run {
            addNextIntentWithParentStack(dangerButtonIntent)
            getPendingIntent(0, flags)
        }
        return pendingIntent ?: throw Throwable("Unable to create pending intent")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channel = NotificationChannel(
            NotificationConstants.CHANNEL_ID,
            NotificationConstants.CHANNEL_NAME,
            NotificationManager.IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)
    }
}