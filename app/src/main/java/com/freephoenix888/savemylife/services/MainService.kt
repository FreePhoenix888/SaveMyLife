package com.freephoenix888.savemylife.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.net.toUri
import androidx.lifecycle.LifecycleService
import com.freephoenix888.savemylife.R
import com.freephoenix888.savemylife.Utils
import com.freephoenix888.savemylife.broadcastReceivers.PowerButtonBroadcastReceiver
import com.freephoenix888.savemylife.broadcastReceivers.RestartBroadcastReceiver
import com.freephoenix888.savemylife.constants.ActionConstants
import com.freephoenix888.savemylife.constants.Constants
import com.freephoenix888.savemylife.constants.MessageConstants
import com.freephoenix888.savemylife.constants.NotificationConstants
import com.freephoenix888.savemylife.domain.models.PhoneNumber
import com.freephoenix888.savemylife.domain.useCases.GetIsDangerModeEnabledFlowUseCase
import com.freephoenix888.savemylife.domain.useCases.GetMessageSendingIntervalFlowUseCase
import com.freephoenix888.savemylife.domain.useCases.GetMessageUseCase
import com.freephoenix888.savemylife.domain.useCases.GetPhoneNumberListFlowUseCase
import com.freephoenix888.savemylife.ui.SaveMyLifeActivity
import com.freephoenix888.savemylife.ui.SaveMyLifeScreenEnum
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


@AndroidEntryPoint
class MainService : LifecycleService() {

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
    private var doInDangerJob: Job? = null
    private val messageSendingInterval = MutableStateFlow(MessageConstants.DEFAULT_SENDING_INTERVAL)
    private val phoneNumberList = MutableStateFlow(emptyList<PhoneNumber>())
    private val powerButtonBroadcastReceiver = PowerButtonBroadcastReceiver()


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
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

        registerPowerButtonBroadcastReceiver()

        coroutineScope.launch {
            getMessageSendingIntervalFlowUseCase().collect {
                messageSendingInterval.value = it
                Log.d(null, "new interval in service: ${messageSendingInterval.value}")
            }
        }
        coroutineScope.launch {
            getPhoneNumberListFlowUseCase().collect {
                phoneNumberList.value = it
            }
        }
        coroutineScope.launch {
            getIsDangerModeEnabledFlowUseCase().collect {
                isDangerModeEnabled.value = it
                doInDangerJob?.cancel()
                if (it) {
                    doInDangerJob = doInDanger()
                }
            }
        }

        return START_STICKY
    }

    private fun registerPowerButtonBroadcastReceiver() {
        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_SCREEN_ON)
            addAction(Intent.ACTION_SCREEN_OFF)
        }
        registerReceiver(powerButtonBroadcastReceiver, filter)
    }

    override fun onDestroy() {
        super.onDestroy()
        val broadcastIntent = Intent(this, RestartBroadcastReceiver::class.java)
        sendBroadcast(broadcastIntent)
        unregisterReceiver(powerButtonBroadcastReceiver)
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
        val notificationBuilder = NotificationCompat.Builder(this, NotificationConstants.CHANNEL_ID)
            .setAutoCancel(false)
            .setOngoing(true)
            .setSmallIcon(R.drawable.ic_stat_name)
            .setLargeIcon(
                BitmapFactory.decodeResource(applicationContext.resources, R.mipmap.ic_launcher))
            .setContentTitle("SaveMyLife")
            .setContentText("SaveMyLife is active")
            .setContentIntent(getDangerButtonActivityPendingIntent())
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

    private fun doInDanger() = coroutineScope.launch {
        while (true) {
            for (phoneNumber in phoneNumberList.value) {
                Utils.sendSms(
                    context = applicationContext,
                    phoneNumber = phoneNumber.phoneNumber,
                    message = getMessageUseCase(phoneNumber)
                )
            }
            delay(messageSendingInterval.value)
        }
    }


}