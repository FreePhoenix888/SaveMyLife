package com.freephoenix888.savemylife.data.sources

import android.content.Context
import com.freephoenix888.savemylife.SecondsInterval
import com.freephoenix888.savemylife.data.datastore.EmergencyMessagePreferencesSerializer.emergencyMessagePreferencesDataStore
import com.freephoenix888.savemylife.data.sources.interfaces.EmergencyMessageLocalDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EmergencyMessageDataStoreLocalDataSource @Inject constructor(@ApplicationContext val context: Context) :
    EmergencyMessageLocalDataSource {

    override val messageTemplate: Flow<String> = context.emergencyMessagePreferencesDataStore.data.map {
        it.messageTemplate
    }

    override suspend fun setMessageTemplate(newMessageTemplate: String) {
        context.emergencyMessagePreferencesDataStore.updateData {
            it.toBuilder()
                .setMessageTemplate(newMessageTemplate)
                .build()
        }
    }

    override val sendingInterval: Flow<SecondsInterval> = context.emergencyMessagePreferencesDataStore.data.map {
        it.sendingIntervalInSeconds
    }

    override suspend fun setSendingInterval(newSendingInterval: SecondsInterval) {
        context.emergencyMessagePreferencesDataStore.updateData {
            it.toBuilder()
                .setSendingIntervalInSeconds(newSendingInterval)
                .build()
        }
    }

}
