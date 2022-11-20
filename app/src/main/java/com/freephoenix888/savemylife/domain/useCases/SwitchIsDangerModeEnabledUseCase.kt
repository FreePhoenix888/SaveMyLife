package com.freephoenix888.savemylife.domain.useCases

import android.util.Log
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class SwitchIsDangerModeEnabledUseCase @Inject constructor(
    val getIsDangerModeEnabledFlowUseCase: GetIsDangerModeEnabledFlowUseCase,
    val setIsDangerModeEnabledUseCase: SetIsDangerModeEnabledUseCase
) {
    suspend operator fun invoke() {
        Log.d(null, "SwitchIsDangerModeEnabledUseCase: ")
        val oldState = getIsDangerModeEnabledFlowUseCase().first()
        setIsDangerModeEnabledUseCase(!oldState)
    }
}