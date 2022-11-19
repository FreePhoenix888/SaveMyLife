package com.freephoenix888.savemylife.broadcastReceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.freephoenix888.savemylife.services.MainService
import dagger.hilt.android.AndroidEntryPoint


private const val TAG = "RestartBroadcastReceiver"
@AndroidEntryPoint
class RestartBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
       if(intent.action == Intent.ACTION_BOOT_COMPLETED || intent.action == Intent.ACTION_LOCKED_BOOT_COMPLETED) {
           Intent(context, MainService::class.java).also {
               if(Build.VERSION.SDK_INT >= 26) {
                   context.startForegroundService(it)
               } else {
                   context.startService(it)
               }
           }

       }
    }
}
