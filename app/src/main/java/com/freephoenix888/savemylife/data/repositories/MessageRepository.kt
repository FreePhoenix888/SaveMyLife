package com.freephoenix888.savemylife.data.repositories

import com.freephoenix888.savemylife.data.sources.interfaces.MessageLocalDataSource
import com.freephoenix888.savemylife.mappers.MessagePreferencesToMessageSettingsMapper
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Duration

@Singleton
class MessageRepository @Inject constructor(private val localStorage: MessageLocalDataSource, private val messagePreferencesToMessageSettingsMapper: MessagePreferencesToMessageSettingsMapper) {
    val settings = localStorage.settings.map {
        messagePreferencesToMessageSettingsMapper.map(it)
    }

    suspend fun setTemplate(template: String) {
        localStorage.setMessageTemplate(template)
    }

    suspend fun setSendingInterval(newSendingInterval: Duration) {
        localStorage.setSendingInterval(newSendingInterval)
    }
}
