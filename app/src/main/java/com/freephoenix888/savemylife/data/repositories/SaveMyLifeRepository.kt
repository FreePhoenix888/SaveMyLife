package com.freephoenix888.savemylife.data.repositories

import com.freephoenix888.savemylife.data.sources.interfaces.SaveMyLifeLocalDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SaveMyLifeRepository @Inject constructor (private val saveMyLifeLocalDataSource: SaveMyLifeLocalDataSource) {
    val isMainServiceEnabled = saveMyLifeLocalDataSource.isMainServiceEnabled
    suspend fun setMainServiceState(state: Boolean) {
        saveMyLifeLocalDataSource.setIsMainServiceEnabled(state)
    }

    val isDangerModeEnabled = saveMyLifeLocalDataSource.isDangerModeEnabled
    suspend fun setDangerModeState(state: Boolean) {
        saveMyLifeLocalDataSource.setIsDangerModeEnabled(state)
    }
}