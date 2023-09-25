package com.freephoenix888.savemylife.data.sources

import androidx.datastore.core.DataStore
import com.freephoenix888.savemylife.MessagePreferences
import com.freephoenix888.savemylife.constants.MessageConstants
import com.freephoenix888.savemylife.copy
import com.freephoenix888.savemylife.data.sources.interfaces.MessageLocalDataSource
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Duration

@Singleton
class MessageDataStoreLocalDataSource @Inject constructor(private val dataStore: DataStore<MessagePreferences>) :
    MessageLocalDataSource {


    private val TAG = this::class.simpleName

    override val preferences = dataStore.data.map { value: MessagePreferences ->
        Timber.d( "Got value: $value")
        val newValue = value.copy {
            template = if(template == ""){ MessageConstants.DEFAULT_TEMPLATE} else {template}
        }
        Timber.d( "Returning new value: $newValue")
        newValue
    }

    override suspend fun setMessageTemplate(newMessageTemplate: String) {
        Timber.d( "${this::setMessageTemplate.name}: Setting new message template: $newMessageTemplate")
        dataStore.updateData {
            it.toBuilder()
                .setTemplate(newMessageTemplate)
                .build()
        }
    }

    override suspend fun setSendingInterval(newSendingInterval: Duration) {
        dataStore.updateData {
            it.toBuilder()
                .setSendingIntervalInMinutes(newSendingInterval.inWholeMinutes)
                .build()
        }
    }

    override suspend fun setIsMessageCommandsEnabled(newIsMessageCommandsEnabled: Boolean) {
        dataStore.updateData {
            it.toBuilder()
                .setIsMessageCommandsEnabled(newIsMessageCommandsEnabled)
                .build()
        }
    }

}
