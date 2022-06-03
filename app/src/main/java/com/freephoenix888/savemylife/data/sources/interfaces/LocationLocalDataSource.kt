package com.freephoenix888.savemylife.data.sources.interfaces

import kotlinx.coroutines.flow.Flow

interface LocationLocalDataSource {
    val locationSharingState: Flow<Boolean>
    suspend fun setLocationSharingState(newState: Boolean)
}