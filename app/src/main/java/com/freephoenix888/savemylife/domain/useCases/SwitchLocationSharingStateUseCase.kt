package com.freephoenix888.savemylife.domain.useCases

import kotlinx.coroutines.flow.first
import javax.inject.Inject

class SwitchLocationSharingStateUseCase @Inject constructor(val getLocationSharingStateUseCase: GetLocationSharingStateFlowUseCase, val setLocationSharingStateUseCase: SetLocationSharingStateUseCase) {
    suspend operator fun invoke() {
        val oldValue = getLocationSharingStateUseCase().first()
        setLocationSharingStateUseCase(!oldValue)
    }
}