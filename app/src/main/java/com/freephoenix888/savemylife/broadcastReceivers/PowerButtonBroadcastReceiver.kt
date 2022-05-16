package com.freephoenix888.savemylife.broadcastReceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.freephoenix888.savemylife.constants.ActionConstants
import com.freephoenix888.savemylife.services.MainService
import java.util.*
import kotlin.concurrent.schedule

class PowerButtonBroadcastReceiver: BroadcastReceiver() {

    companion object {
        val TAG = this::class.simpleName
    }

    private var _count = 0
    private val timer = Timer().schedule(5000) {
        Log.d(TAG, "Timer: _count = 0")
        _count = 0
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(TAG, "onReceive: ")
        val intentAction = intent?.action
        if(intentAction == Intent.ACTION_SCREEN_OFF || intentAction == Intent.ACTION_SCREEN_ON){
            if(_count == 0) {
                timer.run()
            }
            if(_count == 5) {
                Intent(context, MainService::class.java).also {
                    it.action = ActionConstants.SWITCH_DANGER_MODE
                    context?.startService(it)
                }
            }
            ++_count
            Log.d(TAG, "Power button is clicked. _count: $_count")
        }
    }
}