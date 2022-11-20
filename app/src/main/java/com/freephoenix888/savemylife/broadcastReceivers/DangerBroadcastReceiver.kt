package com.freephoenix888.savemylife.broadcastReceivers

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.freephoenix888.savemylife.domain.useCases.GetMessageSendingIntervalFlowUseCase
import com.freephoenix888.savemylife.domain.useCases.GetMessageUseCase
import com.freephoenix888.savemylife.domain.useCases.GetPhoneNumberListFlowUseCase
import com.freephoenix888.savemylife.domain.useCases.SendSmsUseCase
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


private const val TAG = "RestartBroadcastReceiver"
var alarmIntent: PendingIntent? = null

@AndroidEntryPoint
class AlarmBroadcastReceiver : BroadcastReceiver() {

    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    @Inject
    lateinit var getPhoneNumberListFlowUseCase: GetPhoneNumberListFlowUseCase
    @Inject
    lateinit var getMessageUseCase: GetMessageUseCase
    @Inject
    lateinit var getMessageSendingIntervalFlowUseCase: GetMessageSendingIntervalFlowUseCase
    @Inject
    lateinit var sendSmsUseCase: SendSmsUseCase
    @Inject
    @ApplicationContext
    lateinit var applicationContext: Context
    override fun onReceive(context: Context, intent: Intent) {
        coroutineScope.launch {
            val phoneNumbers = getPhoneNumberListFlowUseCase().first()
            for (phoneNumber in phoneNumbers) {
                sendSmsUseCase(
                    context = applicationContext,
                    phoneNumber = phoneNumber.phoneNumber,
                    message = getMessageUseCase(phoneNumber)
                )
            }
            val dangerBroadcastReceiverIntent =
                Intent(applicationContext, AlarmBroadcastReceiver::class.java)
            alarmIntent = PendingIntent.getBroadcast(
                applicationContext,
                0,
                dangerBroadcastReceiverIntent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
            val alarmManager = applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                System.currentTimeMillis() + getMessageSendingIntervalFlowUseCase().first().inWholeMilliseconds, alarmIntent)
        }
    }
}
