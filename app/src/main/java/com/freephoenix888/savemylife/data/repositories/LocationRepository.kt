package com.freephoenix888.savemylife.data.repositories

import com.freephoenix888.savemylife.data.sources.interfaces.LocationLocalDataSource
import com.freephoenix888.savemylife.mappers.LocationPreferencesToLocationSettingsMapper
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationRepository @Inject constructor(private val locationLocalDataSource: LocationLocalDataSource, private val locationPreferencesToLocationSettingsMapper: LocationPreferencesToLocationSettingsMapper) {
    val settings = locationLocalDataSource.preferences.map {
        locationPreferencesToLocationSettingsMapper.map(it)
    }

    suspend fun setIsLocationSharingEnabled(newLocationSharingState: Boolean) {
        locationLocalDataSource.setIsLocationSharingEnabled(newLocationSharingState)
    }
}
