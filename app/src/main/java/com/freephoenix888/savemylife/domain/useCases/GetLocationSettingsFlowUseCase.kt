package com.freephoenix888.savemylife.domain.useCases

import com.freephoenix888.savemylife.data.repositories.LocationRepository
import com.freephoenix888.savemylife.domain.models.LocationSettings
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocationSettingsFlowUseCase @Inject constructor(val locationRepository: LocationRepository) {
    operator fun invoke(): Flow<LocationSettings> {
        return locationRepository.settings
    }
}
