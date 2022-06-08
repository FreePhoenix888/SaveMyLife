package com.freephoenix888.savemylife.di.modules

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.freephoenix888.savemylife.EmergencyMessagePreferences
import com.freephoenix888.savemylife.LocationPreferences
import com.freephoenix888.savemylife.SaveMyLifePreferences
import com.freephoenix888.savemylife.data.datastore.EmergencyMessagePreferencesSerializer
import com.freephoenix888.savemylife.data.datastore.LocationPreferencesSerializer
import com.freephoenix888.savemylife.data.datastore.SaveMyLifePreferencesSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataStoreModule {
    @Singleton
    @Provides
    fun provideSaveMyLifeDataStore(@ApplicationContext applicationContext: Context): DataStore<SaveMyLifePreferences> =
        DataStoreFactory.create(
            serializer = SaveMyLifePreferencesSerializer,
            produceFile = { applicationContext.dataStoreFile("save_my_life_preferences.pb") },
            corruptionHandler = null,
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        )

    @Singleton
    @Provides
    fun provideLocationDataStore(@ApplicationContext applicationContext: Context): DataStore<LocationPreferences> =
        DataStoreFactory.create(
            serializer = LocationPreferencesSerializer,
            produceFile = { applicationContext.dataStoreFile("location_preferences.pb") },
            corruptionHandler = null,
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        )

    @Singleton
    @Provides
    fun provideEmergencyMessageDataStore(@ApplicationContext applicationContext: Context): DataStore<EmergencyMessagePreferences> =
        DataStoreFactory.create(
            serializer = EmergencyMessagePreferencesSerializer,
            produceFile = { applicationContext.dataStoreFile("emergency_message_preferences.pb") },
            corruptionHandler = null,
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        )
}