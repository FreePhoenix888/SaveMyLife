package com.freephoenix888.savemylife.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.os.Build
import android.provider.Telephony
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.freephoenix888.savemylife.R
import com.freephoenix888.savemylife.broadcastReceivers.DangerBroadcastReceiver
import com.freephoenix888.savemylife.broadcastReceivers.DangerCancellationBroadcastReceiver
import com.freephoenix888.savemylife.broadcastReceivers.PowerButtonBroadcastReceiver
import com.freephoenix888.savemylife.broadcastReceivers.SmsBroadcastReceiver
import com.freephoenix888.savemylife.constants.NotificationConstants
import com.freephoenix888.savemylife.domain.models.PhoneNumber
import com.freephoenix888.savemylife.domain.useCases.GetIsDangerModeEnabledFlowUseCase
import com.freephoenix888.savemylife.domain.useCases.GetIsMainServiceEnabledFlowUseCase
import com.freephoenix888.savemylife.domain.useCases.GetMessageSettingsFlowUseCase
import com.freephoenix888.savemylife.domain.useCases.GetPhoneNumberListFlowUseCase
import com.freephoenix888.savemylife.ui.SaveMyLifeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainService : LifecycleService() {

    companion object {
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
    private val phoneNumberList = MutableStateFlow(emptyList<PhoneNumber>())
    private val powerButtonBroadcastReceiver = PowerButtonBroadcastReceiver()

    init {
        setupLifecycleObservers()
    }

    override fun onCreate() {
        super.onCreate()
        registerPowerButtonBroadcastReceiver()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Timber.d( "onStartCommand: ")
        startForegroundService()
        return super.onStartCommand(intent, flags, startId).also { START_STICKY }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(powerButtonBroadcastReceiver)
        smsBroadcastReceiver?.let { unregisterReceiver(it) }
    }

    private fun setupLifecycleObservers() {
        lifecycleScope.launch {
            setupIsDangerModeEnabledObserver()
            setupIsMainServiceEnabledObserver()
            setupPhoneNumberListObserver()
            setupMessageSettingsObserver()
            setupDangerModeBeforeStartTimerObserver()
        }
    }

    private suspend fun setupIsDangerModeEnabledObserver() {
        getIsDangerModeEnabledFlowUseCase().collect { isDangerModeEnabled ->
            val intentClass = if (isDangerModeEnabled) DangerBroadcastReceiver::class.java else DangerCancellationBroadcastReceiver::class.java
            sendBroadcast(Intent(applicationContext, intentClass))
        }
    }

    private suspend fun setupIsMainServiceEnabledObserver() {
        getIsMainServiceEnabledFlowUseCase().collect {
            if (!it) stopSelf()
        }
    }

    private suspend fun setupPhoneNumberListObserver() {
        getPhoneNumberListFlowUseCase().collect {
            phoneNumberList.value = it
        }
    }

    private suspend fun setupMessageSettingsObserver() {
        getMessageSettingsFlowUseCase().collect { settings ->
            handleSmsBroadcastReceiver(settings.isMessageCommandsEnabled)
        }
    }

    private fun handleSmsBroadcastReceiver(isMessageCommandsEnabled: Boolean) {
        smsBroadcastReceiver?.let {
            if (isMessageCommandsEnabled) {
                val filter = IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)
                registerReceiver(it, filter, RECEIVER_EXPORTED)
            } else {
                unregisterReceiver(it)
            }
        }
    }

    private val dangerModeBeforeStartTimerInSeconds = MutableStateFlow(5)

    private suspend fun setupDangerModeBeforeStartTimerObserver() {
        dangerModeBeforeStartTimerInSeconds.collect {
            Timber.d( "NEW TIMER VALUE: $it")
        }
    }

    private fun registerPowerButtonBroadcastReceiver() {
        IntentFilter().apply {
            addAction(Intent.ACTION_SCREEN_ON)
            addAction(Intent.ACTION_SCREEN_OFF)
        }.also {
            registerReceiver(powerButtonBroadcastReceiver, it)
        }
    }

    private fun startForegroundService() {
        val notification = getForegroundServiceNotification()
        startForeground(NotificationConstants.ID, notification)
    }

    private fun getForegroundServiceNotification(): Notification {
        return NotificationCompat.Builder(this, NotificationConstants.CHANNEL_ID)
            .setAutoCancel(false)
            .setOngoing(true)
            .setSmallIcon(R.drawable.ic_stat_name)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
            .setContentTitle("SaveMyLife")
            .setContentText("SaveMyLife is active")
            .setContentIntent(
                PendingIntent.getActivity(
                    this,
                    0,
                    Intent(this, SaveMyLifeActivity::class.java),
                    PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
                )
            )
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) createNotificationChannel()
            }.build()
    }

    private fun createNotificationChannel() {
        NotificationChannel(NotificationConstants.CHANNEL_ID, getString(R.string.all_app_name), NotificationManager.IMPORTANCE_HIGH).apply {
            description = getString(R.string.all_app_name)
            getSystemService(NotificationManager::class.java)?.createNotificationChannel(this)
        }
    }
}
