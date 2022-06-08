package com.freephoenix888.savemylife.domain.useCases

import kotlinx.coroutines.flow.last
import javax.inject.Inject

class SwitchIsDangerModeEnabledUseCase @Inject constructor(
    val getIsDangerModeEnabledFlowUseCase: GetIsDangerModeEnabledFlowUseCase,
    val setDangerModeStateUseCase: SetDangerModeStateUseCase
) {
    suspend operator fun invoke() {
        val newState = !getIsDangerModeEnabledFlowUseCase().last()
        setDangerModeStateUseCase(newState)
    }
}