package com.freephoenix888.savemylife.data.sources.interfaces

import kotlinx.coroutines.flow.Flow

interface LocationLocalDataSource {
    val isLocationSharingEnabled: Flow<Boolean>
    suspend fun setIsLocationSharingEnabled(newState: Boolean)
}