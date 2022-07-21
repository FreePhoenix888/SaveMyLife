package com.freephoenix888.savemylife.broadcastReceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.freephoenix888.savemylife.services.MainService
import dagger.hilt.android.AndroidEntryPoint


private const val TAG = "RestartBroadcastReceiver"
@AndroidEntryPoint
class RestartBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
       if(intent.action == Intent.ACTION_BOOT_COMPLETED) {
           context.startService(Intent(context, MainService::class.java))
       }
    }
}
