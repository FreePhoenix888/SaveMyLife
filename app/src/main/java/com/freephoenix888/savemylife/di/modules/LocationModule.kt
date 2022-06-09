package com.freephoenix888.savemylife.di.modules

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.freephoenix888.savemylife.LocationPreferences
import com.freephoenix888.savemylife.data.datastore.LocationPreferencesSerializer
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocationModule {
    @Singleton
    @Provides
    fun provideLocationDataStore(@ApplicationContext applicationContext: Context): DataStore<LocationPreferences> =
        DataStoreFactory.create(
            serializer = LocationPreferencesSerializer,
            produceFile = { applicationContext.dataStoreFile("location_preferences.pb") },
            corruptionHandler = null,
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        )

    @Provides
    @Singleton
    fun provideGoogleApiAvailability() = GoogleApiAvailability.getInstance()

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(
        @ApplicationContext applicationContext: Context
    ) = LocationServices.getFusedLocationProviderClient(applicationContext)
}