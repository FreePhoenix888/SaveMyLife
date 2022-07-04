package com.freephoenix888.savemylife.broadcastReceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.freephoenix888.savemylife.domain.useCases.SetIsDangerModeEnabledUseCase
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.schedule

@AndroidEntryPoint
class PowerButtonBroadcastReceiver :
    BroadcastReceiver() {

    @Inject
    @ApplicationContext
    lateinit var applicationContext: Context

    @Inject
    lateinit var setIsDangerModeEnabledUseCase: SetIsDangerModeEnabledUseCase

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    val TAG = this::class.java.simpleName

    private var _count = 0
    private val _timer = Timer().schedule(5000) {
        Log.d(TAG, "Timer: _count = 0")
        _count = 0
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(applicationContext, "power button pressed", Toast.LENGTH_LONG).show()

        val intentAction = intent?.action ?: return
        if (intentAction == Intent.ACTION_SCREEN_OFF || intentAction == Intent.ACTION_SCREEN_ON) {
            if (_count == 0) {
                _timer.run()
            }
            if (_count == 5) {
                scope.launch {
                    setIsDangerModeEnabledUseCase(true)
                }
//                scope.launch {
//                    saveMyLifeRepository.setIsDangerModeEnabled(true)
//                }
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