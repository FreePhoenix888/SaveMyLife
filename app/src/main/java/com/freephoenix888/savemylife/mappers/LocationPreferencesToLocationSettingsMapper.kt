package com.freephoenix888.savemylife.mappers

import com.freephoenix888.savemylife.LocationPreferences
import com.freephoenix888.savemylife.domain.models.LocationSettings
import javax.inject.Inject

class LocationPreferencesToLocationSettingsMapper @Inject constructor(): Mapper<LocationPreferences, LocationSettings> {
    override fun map(input: LocationPreferences): LocationSettings {
        return LocationSettings(
            isLocationSharingEnabled = input.isLocationSharingEnabled
        )
    }
}