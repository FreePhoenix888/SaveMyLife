package com.freephoenix888.savemylife.data.sources

import androidx.datastore.core.DataStore
import com.freephoenix888.savemylife.SaveMyLifePreferences
import com.freephoenix888.savemylife.data.sources.interfaces.SaveMyLifeLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SaveMyLifeDataStoreLocalDataSource @Inject constructor(private val dataStore: DataStore<SaveMyLifePreferences>) :
    SaveMyLifeLocalDataSource {

    override val isMainServiceEnabled: Flow<Boolean> = dataStore.data.map {
        it.isMainServiceEnabled
    }

    override suspend fun setIsMainServiceEnabled(newState: Boolean){
        dataStore.updateData {
            it.toBuilder()
                .setIsMainServiceEnabled(newState)
                .build()
        }
    }

    override val isDangerModeEnabled: Flow<Boolean> = dataStore.data.map {
        it.isDangerModeEnabled
    }

    override suspend fun setIsDangerModeEnabled(newState: Boolean) {
        dataStore.updateData {
            it.toBuilder()
                .setIsDangerModeEnabled(newState)
                .build()
        }
    }

}
