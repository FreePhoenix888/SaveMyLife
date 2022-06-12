package com.freephoenix888.savemylife.data.sources

import androidx.datastore.core.DataStore
import com.freephoenix888.savemylife.MessagePreferences
import com.freephoenix888.savemylife.SecondsInterval
import com.freephoenix888.savemylife.data.sources.interfaces.MessageLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageDataStoreLocalDataSource @Inject constructor(private val dataStore: DataStore<MessagePreferences>) :
    MessageLocalDataSource {

    override val messageTemplate: Flow<String> = dataStore.data.map {
        it.messageTemplate
    }

    override suspend fun setMessageTemplate(newMessageTemplate: String) {
        dataStore.updateData {
            it.toBuilder()
                .setMessageTemplate(newMessageTemplate)
                .build()
        }
    }

    override val sendingInterval: Flow<SecondsInterval> = dataStore.data.map {
        it.sendingIntervalInSeconds
    }

    override suspend fun setSendingInterval(newSendingInterval: SecondsInterval) {
        dataStore.updateData {
            it.toBuilder()
                .setSendingIntervalInSeconds(newSendingInterval)
                .build()
        }
    }

}
