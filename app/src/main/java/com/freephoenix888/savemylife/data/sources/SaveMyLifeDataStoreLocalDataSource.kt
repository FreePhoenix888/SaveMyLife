package com.freephoenix888.savemylife.data.sources

import androidx.datastore.core.DataStore
import com.freephoenix888.savemylife.SaveMyLifePreferences
import com.freephoenix888.savemylife.data.sources.interfaces.SaveMyLifeLocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SaveMyLifeDataStoreLocalDataSource @Inject constructor(private val dataStore: DataStore<SaveMyLifePreferences>) :
    SaveMyLifeLocalDataSource {

    override val preferences: Flow<SaveMyLifePreferences> = dataStore.data

    override suspend fun setIsMainServiceEnabled(newState: Boolean){
        dataStore.updateData {
            it.toBuilder()
                .setIsMainServiceEnabled(newState)
                .build()
        }
    }

    override suspend fun setIsAlarmModeEnabled(newState: Boolean) {
        dataStore.updateData {
            it.toBuilder()
                .setIsAlarmModeEnabled(newState)
                .build()
        }
    }

}
