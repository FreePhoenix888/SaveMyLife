package com.freephoenix888.savemylife.broadcastReceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.freephoenix888.savemylife.data.repositories.SaveMyLifeRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.runBlocking
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.concurrent.schedule

@Singleton
class PowerButtonBroadcastReceiver @Inject constructor(@ApplicationContext private val applicationContext: Context, private val saveMyLifeRepository: SaveMyLifeRepository): BroadcastReceiver() {

    val TAG = this::class.java.simpleName

    private var _count = 0
    private val _timer = Timer().schedule(5000) {
        Log.d(TAG, "Timer: _count = 0")
        _count = 0
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val intentAction = intent?.action ?: return
        if(intentAction == Intent.ACTION_SCREEN_OFF || intentAction == Intent.ACTION_SCREEN_ON){
            if(_count == 0) {
                _timer.run()
            }
            if(_count == 5) {
                runBlocking {
                    saveMyLifeRepository.setIsDangerModeEnabled(true)
                }
//                Intent(context, MainService::class.java).also {
//                    it.action = ActionConstants.SwitchDangerMode
//                    context?.startService(it)
//                }

            } else {
                ++_count
            }
            Log.d(TAG, "Power button is clicked. _count: $_count")
        }
    }
}