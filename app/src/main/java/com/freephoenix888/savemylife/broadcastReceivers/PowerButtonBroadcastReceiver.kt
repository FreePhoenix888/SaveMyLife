package com.freephoenix888.savemylife.broadcastReceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.freephoenix888.savemylife.domain.useCases.OpenDangerModeActivationConfirmationScreenUseCase
import com.freephoenix888.savemylife.domain.useCases.SetIsDangerModeEnabledUseCase
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.schedule

@AndroidEntryPoint
class PowerButtonBroadcastReceiver : BroadcastReceiver() {

    @Inject
    @ApplicationContext
    lateinit var applicationContext: Context

    @Inject
    lateinit var setIsDangerModeEnabledUseCase: SetIsDangerModeEnabledUseCase

    @Inject
    lateinit var enableDangerModeUseCase: OpenDangerModeActivationConfirmationScreenUseCase

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    val TAG = this::class.java.simpleName

    private var _count = 0

    override fun onReceive(context: Context, intent: Intent?) {
        val intentAction = intent?.action ?: return
        val isScreenOnOrOff = intentAction == Intent.ACTION_SCREEN_OFF || intentAction == Intent.ACTION_SCREEN_ON
        if (!isScreenOnOrOff) return

        _count++
        if (_count == 1) {
            scope.launch {
                delay(5000)
                _count = 0
            }
        }
        if (_count == 5) {
            scope.launch {
                enableDangerModeUseCase.invoke(context)
                _count = 0
            }
        }
    }
}
