package com.freephoenix888.savemylife.data.repositories

import com.freephoenix888.savemylife.data.sources.interfaces.SaveMyLifeLocalDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SaveMyLifeRepository @Inject constructor (private val saveMyLifeLocalDataSource: SaveMyLifeLocalDataSource) {
    val mainServiceState = saveMyLifeLocalDataSource.mainServiceState
    suspend fun setMainServiceState(state: Boolean) {
        saveMyLifeLocalDataSource.setMainServiceState(state)
    }

    val dangerModeState = saveMyLifeLocalDataSource.dangerModeState
    suspend fun setDangerModeState(state: Boolean) {
        saveMyLifeLocalDataSource.setDangerModeState(state)
    }
}