package com.freephoenix888.savemylife.domain.useCases

import kotlinx.coroutines.flow.first
import javax.inject.Inject

class SwitchIsMainServiceEnabledUseCase @Inject constructor(val getIsMainServiceEnabledFlowUseCase: GetIsMainServiceEnabledFlowUseCase, val setMainServiceStateUseCase: SetMainServiceStateUseCase) {
    suspend operator fun invoke() {
        val oldValue = getIsMainServiceEnabledFlowUseCase().first()
        setMainServiceStateUseCase(!oldValue)
    }
}