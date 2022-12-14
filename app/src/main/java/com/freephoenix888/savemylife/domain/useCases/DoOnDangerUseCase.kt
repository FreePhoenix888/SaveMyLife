package com.freephoenix888.savemylife.domain.useCases

import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DoOnDangerUseCase @Inject constructor(
    @ApplicationContext private val applicationContext: Context,
    private val sendSmsUseCase: SendSmsUseCase,
    private val getMessageUseCase: GetMessageUseCase,
    private val getPhoneNumberListFlowUseCase: GetPhoneNumberListFlowUseCase,
) {
    suspend operator fun invoke() {
        val phoneNumbers = getPhoneNumberListFlowUseCase().first()
        for (phoneNumber in phoneNumbers) {
            sendSmsUseCase(
                context = applicationContext,
                phoneNumber = phoneNumber.phoneNumber,
                message = getMessageUseCase(phoneNumber)
            )
        }
    }
}