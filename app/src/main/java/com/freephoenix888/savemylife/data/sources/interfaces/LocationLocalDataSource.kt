package com.freephoenix888.savemylife.data.sources.interfaces

import com.freephoenix888.savemylife.LocationPreferences
import kotlinx.coroutines.flow.Flow

interface LocationLocalDataSource {
    val preferences: Flow<LocationPreferences>
    suspend fun setIsLocationSharingEnabled(newState: Boolean)
}