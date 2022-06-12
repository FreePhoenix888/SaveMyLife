package com.freephoenix888.savemylife.domain.useCases

import com.freephoenix888.savemylife.data.repositories.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetIsLocationSharingEnabledFlowUseCase @Inject constructor(val locationRepository: LocationRepository) {
    operator fun invoke(): Flow<Boolean> {
        return locationRepository.isLocationSharingEnabled
    }
}
