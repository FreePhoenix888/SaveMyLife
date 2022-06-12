package com.freephoenix888.savemylife.domain.useCases

import com.freephoenix888.savemylife.data.repositories.LocationRepository
import javax.inject.Inject

class SetIsLocationSharingEnabledUseCase @Inject constructor(val locationRepository: LocationRepository) {

    suspend operator fun invoke(newState: Boolean) {
        locationRepository.setIsLocationSharingEnabled(newState)
    }
}