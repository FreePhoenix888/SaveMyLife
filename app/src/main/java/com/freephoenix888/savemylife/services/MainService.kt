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
import android.telephony.SmsManager
import androidx.core.app.TaskStackBuilder
import androidx.core.net.toUri
import com.freephoenix888.savemylife.ui.SaveMyLifeActivity
import com.freephoenix888.savemylife.R
import com.freephoenix888.savemylife.broadcastReceivers.PowerButtonBroadcastReceiver
import com.freephoenix888.savemylife.constants.ActionConstants
import com.freephoenix888.savemylife.constants.Constants
import com.freephoenix888.savemylife.constants.NotificationConstants
import com.freephoenix888.savemylife.domain.models.Contact
import com.freephoenix888.savemylife.domain.useCases.GetEmergencyContactsFlowUseCase
import com.freephoenix888.savemylife.domain.useCases.GetEmergencyMessageUseCase
import com.freephoenix888.savemylife.ui.SaveMyLifeScreenEnum
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject


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
            ActionConstants.StartMainService -> {
                if(isFirstStart) {
                    Log.d(TAG, "First start...")
                    startForegroundService()
                    isFirstStart = false
                }
            }
            ActionConstants.StopMainService -> {
                Log.d(TAG, "Stopping...")
            }
            ActionConstants.SwitchDangerMode -> {
                isDangerModeEnabled = !isDangerModeEnabled
                Log.d(TAG, "Switching danger mode to $isDangerModeEnabled")
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
            it.action = ActionConstants.ShowEmergencyButtonScreen
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

    private fun sendMessageToContact(phoneNumber: String, message: String) {
        val sentPI: PendingIntent = PendingIntent.getBroadcast(this, 0, Intent("SMS_SENT"), 0)
        val smsManager: SmsManager = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            applicationContext.getSystemService(SmsManager::class.java)
        } else {
            SmsManager.getDefault()
        }
        smsManager.sendTextMessage(phoneNumber, null, message, sentPI, null)
    }

    private suspend fun doOnDangerSituation(){

        getEmergencyContactsFlowUseCase().collect { contacts: List<Contact> ->
            for (contact in contacts) {
                for (phoneNumber in contact.phoneNumbers) {
                    sendMessageToContact(
                        phoneNumber = phoneNumber,
                        message = getEmergencyMessageUseCase(contact = contact)
                    )
                }
            }
        }
    }

}