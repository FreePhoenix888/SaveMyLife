package com.freephoenix888.savemylife.data.sources

import android.content.Context
import com.freephoenix888.savemylife.data.datastore.LocationSharingPreferencesSerializer.locationSharingPreferencesDataStore
import com.freephoenix888.savemylife.data.sources.interfaces.LocationLocalDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationDataStoreLocalDataSource @Inject constructor(@ApplicationContext val context: Context) :
    LocationLocalDataSource {
    override val locationSharingState: Flow<Boolean> = context.locationSharingPreferencesDataStore.data.map {
        it.state
    }

    override suspend fun setLocationSharingState(newState: Boolean) {
        context.locationSharingPreferencesDataStore.updateData {
            it.toBuilder()
                .setState(newState)
                .build()
        }
    }

}
