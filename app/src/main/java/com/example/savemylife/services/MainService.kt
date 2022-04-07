package com.example.savemylife.services

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import com.example.savemylife.R
import com.example.savemylife.broadcastReceivers.RestartBroadcastReceiver
import com.example.savemylife.constants.Constants
import android.app.PendingIntent
import android.graphics.BitmapFactory
import com.example.savemylife.MainActivity


class MainService : LifecycleService() {

    var isFirstStart = true

    companion object {
        private const val TAG = "MainService"
    }

    init {
        Log.i(TAG, "Initializing...")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        intent?.let {
            when (it.action) {
                Constants.ACTION_START_SERVICE -> {
                    if(isFirstStart) {
                        Log.d(TAG, "First start...")
                        startForegroundService()
                        isFirstStart = false
                    } else return -1
                }
                Constants.ACTION_STOP_SERVICE -> {
                    Log.d(TAG, "Stopping...")
                }
                else -> {}
            }
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
        startForeground(Constants.NOTIFICATION_ID, notification)
    }

    private fun getNotification(): Notification{
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createNotificationChannel(notificationManager)
        }
        val notificationBuilder = NotificationCompat.Builder(this, Constants.NOTIFICATION_CHANNEL_ID)
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
        val intent = Intent(this, MainActivity::class.java).also {
            it.action = Constants.ACTION_SHOW_EMERGENCY_BUTTON_FRAGMENT
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
        val channel = NotificationChannel(Constants.NOTIFICATION_CHANNEL_ID, Constants.NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW)
        notificationManager.createNotificationChannel(channel)
    }

}