package com.freephoenix888.savemylife.data.repositories

import com.freephoenix888.savemylife.data.sources.interfaces.MainServiceLocalDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainServiceRepository @Inject constructor (val mainServiceLocalDataSource: MainServiceLocalDataSource) {
    val mainServiceState = mainServiceLocalDataSource.state
    suspend fun setState(state: Boolean) {
        mainServiceLocalDataSource.setState(state)
    }

    val dangerModeState = mainServiceLocalDataSource.dangerModeState
    suspend fun setDangerModeState(state: Boolean) {
        mainServiceLocalDataSource.setDangerModeState(state)
    }
}