package com.freephoenix888.savemylife.broadcastReceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.freephoenix888.savemylife.Utils
import com.freephoenix888.savemylife.domain.useCases.GetMessageUseCase
import com.freephoenix888.savemylife.domain.useCases.GetPhoneNumberListFlowUseCase
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


private const val TAG = "RestartBroadcastReceiver"
@AndroidEntryPoint
class AlarmBroadcastReceiver : BroadcastReceiver() {

    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    @Inject lateinit var getPhoneNumberListFlowUseCase: GetPhoneNumberListFlowUseCase
    @Inject lateinit var getMessageUseCase: GetMessageUseCase
    @Inject @ApplicationContext lateinit var applicationContext: Context
    override fun onReceive(context: Context, intent: Intent) {
    coroutineScope.launch {
        val phoneNumbers = getPhoneNumberListFlowUseCase().first()
        for (phoneNumber in phoneNumbers) {
            Utils.sendSms(
                context = applicationContext,
                phoneNumber = phoneNumber.phoneNumber,
                message = getMessageUseCase(phoneNumber)
            )
        }
    }
    }

}
