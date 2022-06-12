package com.freephoenix888.savemylife.data.repositories

import com.freephoenix888.savemylife.data.sources.interfaces.LocationLocalDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationRepository @Inject constructor(private val locationLocalDataSource: LocationLocalDataSource) {
    val isLocationSharingEnabled = locationLocalDataSource.isLocationSharingEnabled
    suspend fun setIsLocationSharingEnabled(newLocationSharingState: Boolean) {
        locationLocalDataSource.setIsLocationSharingEnabled(newLocationSharingState)
    }
}
