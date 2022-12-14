package com.freephoenix888.savemylife.domain.useCases

import com.freephoenix888.savemylife.data.repositories.LocationSharingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetIsLocationSharingEnabledFlowUseCase @Inject constructor(val locationSharingRepository: LocationSharingRepository) {

    operator fun invoke(): Flow<Boolean> {
        return locationSharingRepository.locationSharingSettings.map { it.isLocationSharingEnabled }
    }
}