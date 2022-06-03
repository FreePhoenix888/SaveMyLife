package com.freephoenix888.savemylife.data.repositories

import com.freephoenix888.savemylife.data.sources.interfaces.LocationLocalDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationRepository @Inject constructor(val locationSettingLocalSource: LocationLocalDataSource) {
    val locationSharingState = locationSettingLocalSource.locationSharingState
    suspend fun setLocationSharingState(newLocationSharingState: Boolean) {
        locationSettingLocalSource.setLocationSharingState(newLocationSharingState)
    }
}
