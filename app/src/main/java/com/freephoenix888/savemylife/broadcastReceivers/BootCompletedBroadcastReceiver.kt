package com.freephoenix888.savemylife.broadcastReceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Environment
import android.util.Log
import com.freephoenix888.savemylife.services.MainService
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream


private const val TAG = "RestartBroadcastReceiver"
@AndroidEntryPoint
class BootCompletedBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val file = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "onboot.txt")
        FileOutputStream(file).use {
            it.write("SAVEMYLIFE onReceive: ${context}  ${intent}".toByteArray())
        }
//        val path = context.getExternalFilesDir(null)
//        val
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
