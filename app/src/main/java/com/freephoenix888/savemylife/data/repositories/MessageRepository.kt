package com.freephoenix888.savemylife.data.repositories

import com.freephoenix888.savemylife.data.sources.interfaces.MessageLocalDataSource
import com.freephoenix888.savemylife.mappers.MessagePreferencesToMessageSettingsMapper
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Duration

@Singleton
class MessageRepository @Inject constructor(private val localStorage: MessageLocalDataSource, private val messagePreferencesToMessageSettingsMapper: MessagePreferencesToMessageSettingsMapper) {

    private val TAG = this::class.simpleName

    val settings = localStorage.preferences.map {
        messagePreferencesToMessageSettingsMapper.map(it)
    }

    suspend fun setTemplate(template: String) {
        Timber.d( "setTemplate: Setting new message template: $template")
        localStorage.setMessageTemplate(template)
    }

    suspend fun setSendingInterval(newSendingInterval: Duration) {
        localStorage.setSendingInterval(newSendingInterval)
    }

    suspend fun setIsMessageCommandsEnabled(newIsMessageCommandsEnabled: Boolean) {
        localStorage.setIsMessageCommandsEnabled(newIsMessageCommandsEnabled)

    }
}
