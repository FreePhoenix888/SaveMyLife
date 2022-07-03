package com.freephoenix888.savemylife.data.sources.interfaces

import com.freephoenix888.savemylife.SaveMyLifePreferences
import kotlinx.coroutines.flow.Flow

interface SaveMyLifeLocalDataSource {
    val preferences: Flow<SaveMyLifePreferences>
    suspend fun setIsMainServiceEnabled(newState: Boolean)
    suspend fun setIsDangerModeEnabled(newState: Boolean)
}