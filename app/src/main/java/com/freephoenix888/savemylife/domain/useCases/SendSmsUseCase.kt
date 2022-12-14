package com.freephoenix888.savemylife.domain.useCases

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.telephony.SmsManager
import javax.inject.Inject

class SendSmsUseCase @Inject constructor() {
    operator fun invoke(context: Context, phoneNumber: String, message: String) {
        val flags = if (Build.VERSION.SDK_INT >= 23) PendingIntent.FLAG_IMMUTABLE else 0
        val sentPI: PendingIntent =
            PendingIntent.getBroadcast(context, 0, Intent("SMS_SENT"), flags)
        @Suppress("DEPRECATION") val smsManager: SmsManager =
            if (Build.VERSION.SDK_INT >= 23) {
                context.getSystemService(SmsManager::class.java)
            } else {
                SmsManager.getDefault()
            }
        smsManager.sendTextMessage(phoneNumber, null, message, sentPI, null)

    }
}