package com.freephoenix888.savemylife.domain.useCases

import android.util.Log
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class SwitchIsAlarmModeEnabledUseCase @Inject constructor(
    val getIsAlarmModeEnabledFlowUseCase: GetIsAlarmModeEnabledFlowUseCase,
    val setIsAlarmModeEnabledUseCase: SetIsAlarmModeEnabledUseCase
) {
    suspend operator fun invoke() {
        Log.d(null, "SwitchIsAlarmModeEnabledUseCase: ")
        val oldState = getIsAlarmModeEnabledFlowUseCase().first()
        setIsAlarmModeEnabledUseCase(!oldState)
    }
}