package com.freephoenix888.savemylife.broadcastReceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.freephoenix888.savemylife.domain.useCases.GetIsMainServiceEnabledFlowUseCase
import com.freephoenix888.savemylife.services.MainService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class BootCompletedBroadcastReceiver : BroadcastReceiver() {

    private val tag = javaClass.simpleName

    @Inject
    lateinit var getIsMainServiceEnabledFlowUseCase: GetIsMainServiceEnabledFlowUseCase

    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun onReceive(context: Context, intent: Intent) {
        Timber.d( "Received intent: ${intent.action}")

        intent.action?.takeIf {
            it == Intent.ACTION_BOOT_COMPLETED || it == Intent.ACTION_LOCKED_BOOT_COMPLETED
        } ?: return

        coroutineScope.launch {
            if (!getIsMainServiceEnabledFlowUseCase().first()) {
                Timber.d( "MainService is not enabled")
                return@launch
            }

            Intent(context, MainService::class.java).also { serviceIntent ->
                context.startForegroundService(serviceIntent)
            }
            Timber.d( "Started MainService")
        }
    }
}
