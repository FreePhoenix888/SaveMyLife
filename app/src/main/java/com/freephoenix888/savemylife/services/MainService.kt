package com.freephoenix888.savemylife.services

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import com.freephoenix888.savemylife.broadcastReceivers.RestartBroadcastReceiver
import android.app.PendingIntent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.telephony.SmsManager
import com.freephoenix888.savemylife.ui.SaveMyLifeActivity
import com.freephoenix888.savemylife.R
import com.freephoenix888.savemylife.SaveMyLifeApplication
import com.freephoenix888.savemylife.Utils.Companion.getContactsByUri
import com.freephoenix888.savemylife.broadcastReceivers.PowerButtonBroadcastReceiver
import com.freephoenix888.savemylife.constants.ActionConstants
import com.freephoenix888.savemylife.constants.NotificationConstants
import com.freephoenix888.savemylife.data.repositories.ContactRepository
import com.freephoenix888.savemylife.data.storage.entities.ContactEntity
import javax.inject.Inject
import kotlin.coroutines.coroutineContext
import kotlinx.coroutines.flow.collect


class MainService : LifecycleService() {

    @Inject lateinit var repository: ContactRepository
    private var isDangerModeEnabled = false
    private val powerButtonBroadcastReceiver = PowerButtonBroadcastReceiver()


    var isFirstStart = true
    private val TAG = this::class.simpleName

    init {
        Log.i(TAG, "Initializing...")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        if(intent == null){
            return START_STICKY
        }
        when (intent.action) {
            ActionConstants.START_SERVICE -> {
                if(isFirstStart) {
                    Log.d(TAG, "First start...")
                    startForegroundService()
                    isFirstStart = false
                }
            }
            ActionConstants.STOP_SERVICE -> {
                Log.d(TAG, "Stopping...")
            }
            ActionConstants.SWITCH_DANGER_MODE -> {
                isDangerModeEnabled = !isDangerModeEnabled
                Log.d(TAG, "Switching danger mode to $isDangerModeEnabled")
            }
            else -> {}
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "$TAG is destroyed.")
        val broadcastIntent = Intent(this, RestartBroadcastReceiver::class.java)
        sendBroadcast(broadcastIntent)
    }

    private fun startForegroundService(){
        val notification = getNotification()
        startForeground(NotificationConstants.ID, notification)
    }

    private fun getNotification(): Notification{
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createNotificationChannel(notificationManager)
        }
        val notificationBuilder = NotificationCompat.Builder(this, NotificationConstants.CHANNEL_ID)
            .setAutoCancel(false)
            .setOngoing(true)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_round_warning_24))
//            .setSmallIcon(R.drawable.ic_round_warning_24)
            .setContentTitle("SaveMyLife")
            .setContentText("SaveMyLife is active")
            .setContentIntent(getEmergencyButtonActivityPendingIntent())

        return notificationBuilder.build()
    }

    private fun getEmergencyButtonActivityPendingIntent(): PendingIntent {
        val pendingIntent: PendingIntent
        val intent = Intent(this, SaveMyLifeActivity::class.java).also {
            it.action = ActionConstants.SHOW_EMERGENCY_BUTTON_FRAGMENT
        }


        val flags: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }
        pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            flags
        )
        return pendingIntent
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager){
        val channel = NotificationChannel(NotificationConstants.CHANNEL_ID, NotificationConstants.CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW)
        notificationManager.createNotificationChannel(channel)
    }

}