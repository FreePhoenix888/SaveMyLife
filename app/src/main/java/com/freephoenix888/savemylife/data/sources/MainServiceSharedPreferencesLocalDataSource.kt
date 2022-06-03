package com.freephoenix888.savemylife.data.sources

import android.content.Context
import com.freephoenix888.savemylife.data.datastore.MainServicePreferencesSerializer.mainServicePreferencesDataStore
import com.freephoenix888.savemylife.data.sources.interfaces.MainServiceLocalDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainServiceSharedPreferencesLocalDataSource @Inject constructor(@ApplicationContext val context: Context) :
    MainServiceLocalDataSource {

    override val state: Flow<Boolean> = context.mainServicePreferencesDataStore.data.map {
        it.state
    }

    override suspend fun setState(newState: Boolean){
        context.mainServicePreferencesDataStore.updateData {
            it.toBuilder()
                .setState(newState)
                .build()
        }
    }

    override val dangerModeState: Flow<Boolean> = context.mainServicePreferencesDataStore.data.map {
        it.dangerModeState
    }

    override suspend fun setDangerModeState(newState: Boolean) {
        context.mainServicePreferencesDataStore.updateData {
            it.toBuilder()
                .setDangerModeState(newState)
                .build()
        }
    }

}
