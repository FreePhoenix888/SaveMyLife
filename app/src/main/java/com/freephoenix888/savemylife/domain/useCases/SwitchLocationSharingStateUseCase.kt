package com.freephoenix888.savemylife.domain.useCases

import kotlinx.coroutines.flow.first
import javax.inject.Inject

class SwitchLocationSharingStateUseCase @Inject constructor(
    val getLocationSharingStateUseCase: GetIsLocationSharingEnabledFlowUseCase,
    val setIsLocationSharingEnabledUseCase: SetIsLocationSharingEnabledUseCase
) {
    suspend operator fun invoke() {
        val oldValue = getLocationSharingStateUseCase().first()
        setIsLocationSharingEnabledUseCase(!oldValue)
    }
}