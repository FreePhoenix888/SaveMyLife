package com.freephoenix888.savemylife.domain.useCases

import com.freephoenix888.savemylife.data.repositories.LocationSharingRepository
import com.freephoenix888.savemylife.domain.models.LocationSettings
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocationSettingsFlowUseCase @Inject constructor(val locationSharingRepository: LocationSharingRepository) {
    operator fun invoke(): Flow<LocationSettings> {
        return locationSharingRepository.locationSharingSettings
    }
}
