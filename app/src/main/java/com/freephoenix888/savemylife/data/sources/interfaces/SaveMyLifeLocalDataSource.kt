package com.freephoenix888.savemylife.data.sources.interfaces

import kotlinx.coroutines.flow.Flow

interface SaveMyLifeLocalDataSource {
    val isMainServiceEnabled: Flow<Boolean>
    suspend fun setIsMainServiceEnabled(newState: Boolean)
    val isDangerModeEnabled: Flow<Boolean>
    suspend fun setIsDangerModeEnabled(newState: Boolean)
}