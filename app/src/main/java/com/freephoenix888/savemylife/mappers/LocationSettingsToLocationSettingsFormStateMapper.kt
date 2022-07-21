package com.freephoenix888.savemylife.mappers

import com.freephoenix888.savemylife.domain.models.LocationSettings
import com.freephoenix888.savemylife.domain.models.LocationSettingsUiState
import javax.inject.Inject

class LocationSettingsToLocationSettingsFormStateMapper @Inject constructor(): Mapper<LocationSettings, LocationSettingsUiState> {
    override fun map(input: LocationSettings): LocationSettingsUiState {
        return LocationSettingsUiState(
            isLocationSharingEnabled = input.isLocationSharingEnabled
        )
    }
}