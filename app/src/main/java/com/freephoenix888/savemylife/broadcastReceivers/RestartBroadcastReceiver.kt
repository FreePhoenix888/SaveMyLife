package com.freephoenix888.savemylife.broadcastReceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.freephoenix888.savemylife.services.MainService
import dagger.hilt.android.AndroidEntryPoint


private const val TAG = "RestartBroadcastReceiver"
@AndroidEntryPoint
class RestartBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        context.startService(Intent(context, MainService::class.java))
        Log.d(null, "RestartBroadcastReceiver onReceive: ")
        StringBuilder().apply {
            append("RestartBroadcastReceiver Action: ${intent.action}\n")
            append("RestartBroadcastReceiver URI: ${intent.toUri(Intent.URI_INTENT_SCHEME)}\n")
            toString().also { log ->
                Log.d(null, log)
                Toast.makeText(context, log, Toast.LENGTH_LONG).show()
            }
        }
    }
}
