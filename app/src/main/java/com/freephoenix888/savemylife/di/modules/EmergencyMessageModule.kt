package com.freephoenix888.savemylife.di.modules

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.freephoenix888.savemylife.EmergencyMessagePreferences
import com.freephoenix888.savemylife.data.datastore.EmergencyMessagePreferencesSerializer
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
object EmergencyMessageModule {
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