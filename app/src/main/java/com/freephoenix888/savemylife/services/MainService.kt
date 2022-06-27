package com.freephoenix888.savemylife.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.net.toUri
import androidx.lifecycle.LifecycleService
import com.freephoenix888.savemylife.R
import com.freephoenix888.savemylife.broadcastReceivers.PowerButtonBroadcastReceiver
import com.freephoenix888.savemylife.broadcastReceivers.RestartBroadcastReceiver
import com.freephoenix888.savemylife.constants.ActionConstants
import com.freephoenix888.savemylife.constants.Constants
import com.freephoenix888.savemylife.constants.NotificationConstants
import com.freephoenix888.savemylife.domain.useCases.GetIsDangerModeEnabledFlowUseCase
import com.freephoenix888.savemylife.domain.useCases.GetMessageSendingIntervalUseCase
import com.freephoenix888.savemylife.domain.useCases.GetMessageUseCase
import com.freephoenix888.savemylife.domain.useCases.GetPhoneNumberListFlowUseCase
import com.freephoenix888.savemylife.ui.SaveMyLifeActivity
import com.freephoenix888.savemylife.ui.SaveMyLifeScreenEnum
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@AndroidEntryPoint
class MainService: LifecycleService() {

    val powerButtonBroadcastReceiver: PowerButtonBroadcastReceiver = PowerButtonBroadcastReceiver()
    @Inject lateinit var getIsDangerModeEnabledFlowUseCase: GetIsDangerModeEnabledFlowUseCase
    @Inject lateinit var getPhoneNumberListFlowUseCase: GetPhoneNumberListFlowUseCase
    @Inject lateinit var getMessageSendingIntervalUseCase: GetMessageSendingIntervalUseCase
    @Inject lateinit var getMessageUseCase: GetMessageUseCase

    var isFirstStart = true
    private val TAG = this::class.simpleName
    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private lateinit var isDangerModeEnabled: Flow<Boolean>
    private var doInDangerSituationJob: Job? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        isDangerModeEnabled = getIsDangerModeEnabledFlowUseCase()
        if (intent == null) {
            return START_STICKY
        }
        when (intent.action) {
            ActionConstants.StartMainService -> {
                if (isFirstStart) {
                    Log.d(TAG, "First start...")
                    startForegroundService()
                    isFirstStart = false
                }
            }
            ActionConstants.StopMainService -> {
                Log.d(TAG, "Stopping...")
            }
            else -> {}
        }
        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_SCREEN_ON)
            addAction(Intent.ACTION_SCREEN_OFF)
        }
        this.registerReceiver(powerButtonBroadcastReceiver, filter)
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "$TAG is destroyed.")
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
//            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_baseline_warning_24))
            .setSmallIcon(R.drawable.ic_baseline_warning_24)
            .setContentTitle("SaveMyLife")
            .setContentText("SaveMyLife is active")
            .setContentIntent(getButtonActivityPendingIntent())

        return notificationBuilder.build()
    }

    private fun getButtonActivityPendingIntent(): PendingIntent {
        val dangerButtonIntent = Intent(
            Intent.ACTION_VIEW,
            "${Constants.APP_URI}/screen/${SaveMyLifeScreenEnum.DangerButton.name}".toUri(),
            applicationContext,
            SaveMyLifeActivity::class.java
        )


//        val pendingIntent: PendingIntent
//        val intent = Intent(this, SaveMyLifeActivity::class.java).also {
//            it.action = ActionConstants.ShowButtonScreen
//        }


        val flags: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }
//        pendingIntent = PendingIntent.getActivity(
//            this,
//            0,
//            intent,
//            flags
//        )
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