package com.freephoenix888.savemylife.data.sources.interfaces

import kotlinx.coroutines.flow.Flow

interface SaveMyLifeLocalDataSource {
    val mainServiceState: Flow<Boolean>
    suspend fun setMainServiceState(newState: Boolean)
    val dangerModeState: Flow<Boolean>
    suspend fun setDangerModeState(newState: Boolean)
}