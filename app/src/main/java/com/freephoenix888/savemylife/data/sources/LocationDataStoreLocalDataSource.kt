package com.freephoenix888.savemylife.data.sources

import androidx.datastore.core.DataStore
import com.freephoenix888.savemylife.LocationPreferences
import com.freephoenix888.savemylife.data.sources.interfaces.LocationLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationDataStoreLocalDataSource @Inject constructor(private val dataStore: DataStore<LocationPreferences>) :
    LocationLocalDataSource {
    override val locationSharingState: Flow<Boolean> = dataStore.data.map {
        it.state
    }

    override suspend fun setLocationSharingState(newState: Boolean) {
        dataStore.updateData {
            it.toBuilder()
                .setState(newState)
                .build()
        }
    }

}
