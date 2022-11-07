package com.freephoenix888.savemylife.broadcastReceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import com.freephoenix888.savemylife.Utils
import com.freephoenix888.savemylife.domain.useCases.GetUserLocationUrlUseCase
import com.freephoenix888.savemylife.enums.MessageCommand
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

class SmsBroadcastReceiver : BroadcastReceiver() {
    private val scope = CoroutineScope(SupervisorJob())

    companion object {
        private val TAG by lazy { SmsBroadcastReceiver::class.java.simpleName }
    }

    @Inject
    @ApplicationContext
    lateinit var applicationContext: Context

    @Inject
    lateinit var getUserLocationUrlUseCase: GetUserLocationUrlUseCase

    override fun onReceive(context: Context, intent: Intent) {
        if (!intent.action.equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) return
        val smsMessages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
        for (smsMessage in smsMessages) {
            val isCommand = smsMessage.messageBody.startsWith("/")
            if (!isCommand) {
                continue
            }
            when (smsMessage.messageBody.substring(1).lowercase()) {
                MessageCommand.LOCATION.name.lowercase() -> {
                    scope.launch(Dispatchers.IO) {
                        smsMessage.originatingAddress?.let { originatingAddress ->
                            Utils.sendSms(
                                context,
                                phoneNumber = originatingAddress,
                                message = getUserLocationUrlUseCase()
                            )
                        }
                    }
                }
            }
        }
    }
}
