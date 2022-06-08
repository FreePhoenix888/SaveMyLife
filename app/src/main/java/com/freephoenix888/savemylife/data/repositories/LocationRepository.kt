package com.freephoenix888.savemylife.data.repositories

import com.freephoenix888.savemylife.data.sources.interfaces.LocationLocalDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationRepository @Inject constructor(private val locationLocalDataSource: LocationLocalDataSource) {
    val locationSharingState = locationLocalDataSource.locationSharingState
    suspend fun setLocationSharingState(newLocationSharingState: Boolean) {
        locationLocalDataSource.setLocationSharingState(newLocationSharingState)
    }
}
