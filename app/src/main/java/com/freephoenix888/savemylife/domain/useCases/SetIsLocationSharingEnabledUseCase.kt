package com.freephoenix888.savemylife.domain.useCases

import com.freephoenix888.savemylife.data.repositories.LocationSharingRepository
import javax.inject.Inject

class SetIsLocationSharingEnabledUseCase @Inject constructor(val locationSharingRepository: LocationSharingRepository) {

    suspend operator fun invoke(newState: Boolean) {
        locationSharingRepository.setIsLocationSharingEnabled(newState)
    }
}