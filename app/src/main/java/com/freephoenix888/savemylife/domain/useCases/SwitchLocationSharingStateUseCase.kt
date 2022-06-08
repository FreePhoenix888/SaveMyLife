package com.freephoenix888.savemylife.domain.useCases

import kotlinx.coroutines.flow.last
import javax.inject.Inject

class SwitchLocationSharingStateUseCase @Inject constructor(val getLocationSharingStateUseCase: GetLocationSharingStateFlowUseCase, val setLocationSharingStateUseCase: SetLocationSharingStateUseCase) {
    suspend operator fun invoke() {
        val newState = !getLocationSharingStateUseCase().last()
        setLocationSharingStateUseCase(newState)
    }
}