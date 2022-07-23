package com.freephoenix888.savemylife.domain.useCases

import com.freephoenix888.savemylife.data.repositories.LocationRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetIsLocationSharingEnabledUseCase @Inject constructor(val locationRepository: LocationRepository) {

    suspend operator fun invoke(newState: Boolean) {
        locationRepository.settings.map { it.isLocationSharingEnabled }
    }
}