package com.freephoenix888.savemylife.domain.useCases

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.telephony.SmsManager
import android.telephony.SubscriptionManager
import androidx.core.app.ActivityCompat
import javax.inject.Inject

class SendSmsUseCase @Inject constructor() {
    operator fun invoke(context: Context, phoneNumber: String, message: String, simId: Int = 0) {
        val flags = if (Build.VERSION.SDK_INT >= 23) PendingIntent.FLAG_IMMUTABLE else 0
        val sentPI: PendingIntent =
            PendingIntent.getBroadcast(context, 0, Intent("SMS_SENT"), flags)

        var subscriptionManager = context.getSystemService(SubscriptionManager::class.java)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_PHONE_STATE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            throw Exception("Manifest.permission.READ_PHONE_STATE permission is not granted")
        }
        if(subscriptionManager.activeSubscriptionInfoCount > 1) {
            val subscriptionInfoList = subscriptionManager.activeSubscriptionInfoList
            val simInfo = subscriptionInfoList[simId]
            val smsManager = if(Build.VERSION.SDK_INT < 31) {
                @Suppress("DEPRECATION")
                SmsManager.getSmsManagerForSubscriptionId(simInfo.subscriptionId)
            } else {
                context.getSystemService(SmsManager::class.java).createForSubscriptionId(simInfo.subscriptionId)
            }

                smsManager.sendTextMessage(phoneNumber, null, message, sentPI, null)
        }


    }
}