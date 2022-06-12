package com.freephoenix888.savemylife.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.telephony.SmsManager
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
import com.freephoenix888.savemylife.ui.SaveMyLifeActivity
import com.freephoenix888.savemylife.ui.SaveMyLifeScreenEnum
import kotlinx.coroutines.*
import javax.inject.Inject


class MainService @Inject constructor(
    private val powerButtonBroadcastReceiver: PowerButtonBroadcastReceiver,
    private val getMessageSendingIntervalUseCase: GetMessageSendingIntervalUseCase,
    private val getMessageUseCase: GetMessageUseCase,
    private val getIsDangerModeEnabledFlowUseCase: GetIsDangerModeEnabledFlowUseCase
) : LifecycleService() {

    var isFirstStart = true
    private val TAG = this::class.simpleName
    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val isDangerModeEnabled = getIsDangerModeEnabledFlowUseCase()
    private var doInDangerSituationJob: Job? = null

    init {
        Log.i(TAG, "Initializing...")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
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
        coroutineScope.launch {
            isDangerModeEnabled.collect {
                if (it) {
                    doInDangerSituationJob = doInDangerSituation()
                } else if (!it) {
                    doInDangerSituationJob?.cancel()
                }
            }
        }
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

    private fun sendMessageToContact(phoneNumber: String, message: String) {
        val sentPI: PendingIntent = PendingIntent.getBroadcast(this, 0, Intent("SMS_SENT"), 0)
        @Suppress("DEPRECATION") val smsManager: SmsManager =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                applicationContext.getSystemService(SmsManager::class.java)
            } else {
                SmsManager.getDefault()
            }
        smsManager.sendTextMessage(phoneNumber, null, message, sentPI, null)
    }

    private suspend fun doInDangerSituation(): Job = coroutineScope.launch {
//        val contactAndPhoneNumbersMap = mutableMapOf<Contact, List<PhoneNumber>>()
//        val contactList = getContactListFlowUseCase().first()
//        contactList.forEach { contact ->
//            val phoneNumbers =
//                getPhoneNumberListByUriFlowUseCase(contact.contentUri).first()
//            contactAndPhoneNumbersMap[contact] = phoneNumbers
//        }
//        val messageSendingInterval = getMessageSendingIntervalUseCase().first()
//        while (true) {
//            for (contactAndPhoneNumbers in contactAndPhoneNumbersMap) {
//                val contact = contactAndPhoneNumbers.key
//                for (contactPhoneNumber in contactAndPhoneNumbers.value) {
//                    sendMessageToContact(
//                        phoneNumber = contactPhoneNumber.contentUri,
//                        message = getMessageUseCase(contact = contact)
//                    )
//                }
//            }
//            delay(messageSendingInterval)
//        }
    }

}