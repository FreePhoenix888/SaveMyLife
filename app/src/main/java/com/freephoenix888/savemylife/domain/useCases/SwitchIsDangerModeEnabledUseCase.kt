package com.freephoenix888.savemylife.domain.useCases

import android.util.Log
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class SwitchIsDangerModeEnabledUseCase @Inject constructor(
    val getIsDangerModeEnabledFlowUseCase: GetIsDangerModeEnabledFlowUseCase,
    val setDangerModeStateUseCase: SetDangerModeStateUseCase
) {
    suspend operator fun invoke() {
        val oldState = getIsDangerModeEnabledFlowUseCase().first()
        setDangerModeStateUseCase(!oldState)
        Log.d(null, "SwitchIsDangerModeEnabledUseCase invoke: ")
    }
}