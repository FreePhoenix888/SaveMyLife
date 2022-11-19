package com.freephoenix888.savemylife.broadcastReceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.freephoenix888.savemylife.domain.useCases.SetIsAlarmModeEnabledUseCase
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
    lateinit var setIsAlarmModeEnabledUseCase: SetIsAlarmModeEnabledUseCase

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    val TAG = this::class.java.simpleName

    private var _count = 0

    override fun onReceive(context: Context?, intent: Intent?) {
        val intentAction = intent?.action ?: return
        val isScreenOnOrOff = intentAction == Intent.ACTION_SCREEN_OFF || intentAction == Intent.ACTION_SCREEN_ON
        if (!isScreenOnOrOff) {
            return
        }
        if (_count == 0) {
            Timer().schedule(5000) {
                _count = 0
            }
        }
        if (_count == 5) {
            scope.launch {
                setIsAlarmModeEnabledUseCase(true)
            }
        }
        _count++

    }
}