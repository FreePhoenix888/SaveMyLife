package com.freephoenix888.savemylife.data.sources.interfaces

import kotlinx.coroutines.flow.Flow

interface MainServiceLocalDataSource {
    val state: Flow<Boolean>
    suspend fun setState(newState: Boolean)
    val dangerModeState: Flow<Boolean>
    suspend fun setDangerModeState(newState: Boolean)
}