package com.freephoenix888.savemylife.broadcastReceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.freephoenix888.savemylife.domain.useCases.GetIsMainServiceEnabledFlowUseCase
import com.freephoenix888.savemylife.services.MainService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


private const val TAG = "RestartBroadcastReceiver"
@AndroidEntryPoint
class BootCompletedBroadcastReceiver : BroadcastReceiver() {

    @Inject
    lateinit var getIsMainServiceEnabledFlowUseCase: GetIsMainServiceEnabledFlowUseCase

    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())


    override fun onReceive(context: Context, intent: Intent) {
        coroutineScope.launch {
            if (intent.action != Intent.ACTION_BOOT_COMPLETED && intent.action != Intent.ACTION_LOCKED_BOOT_COMPLETED) return@launch
            if (!getIsMainServiceEnabledFlowUseCase().first()) return@launch
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
