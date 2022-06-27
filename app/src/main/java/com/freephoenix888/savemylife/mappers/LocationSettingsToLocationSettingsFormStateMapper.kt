package com.freephoenix888.savemylife.mappers

import com.freephoenix888.savemylife.domain.models.LocationSettings
import com.freephoenix888.savemylife.domain.models.LocationSettingsFormState
import javax.inject.Inject

class LocationSettingsToLocationSettingsFormStateMapper @Inject constructor(): Mapper<LocationSettings, LocationSettingsFormState> {
    override fun map(input: LocationSettings): LocationSettingsFormState {
        return LocationSettingsFormState(
            isLocationSharingEnabled = input.isLocationSharingEnabled
        )
    }
}