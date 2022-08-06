package com.freephoenix888.savemylife.broadcastReceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.util.Log
import com.freephoenix888.savemylife.domain.useCases.GetUserLocationUrlUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
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
        val extractMessages = Telephony.Sms.Intents.getMessagesFromIntent(intent)


        extractMessages.forEach { smsMessage ->
            Log.v(TAG, smsMessage.displayMessageBody)
            Log.v(TAG, smsMessage.toString())
            Log.v(TAG, smsMessage.displayOriginatingAddress)
//            when (smsMessage.messageBody) {
//                "/${MessageCommands.LOCATION.name.lowercase()}" -> {
//                    if (smsMessage.originatingAddress != null) {
//                        scope.launch(Dispatchers.IO) {
//                            Utils.sendSms(
//                                context,
//                                phoneNumber = smsMessage.originatingAddress!!,
//                                message = getUserLocationUrlUseCase()
//                            )
//                        }
//                    }
//
//                }
//            }

        }
    }
}
