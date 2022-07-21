package com.freephoenix888.savemylife.data.sources

import androidx.datastore.core.DataStore
import com.freephoenix888.savemylife.MessagePreferences
import com.freephoenix888.savemylife.data.sources.interfaces.MessageLocalDataSource
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Duration

@Singleton
class MessageDataStoreLocalDataSource @Inject constructor(private val dataStore: DataStore<MessagePreferences>) :
    MessageLocalDataSource {

    override val preferences = dataStore.data

    override suspend fun setMessageTemplate(newMessageTemplate: String) {
        dataStore.updateData {
            it.toBuilder()
                .setTemplate(newMessageTemplate)
                .build()
        }
    }

    override suspend fun setSendingInterval(newSendingInterval: Duration) {
        dataStore.updateData {
            it.toBuilder()
                .setSendingIntervalInMinutes(newSendingInterval.inWholeSeconds)
                .build()
        }
    }

}
