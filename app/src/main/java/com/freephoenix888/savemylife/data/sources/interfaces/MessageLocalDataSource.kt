package com.freephoenix888.savemylife.data.sources.interfaces

import com.freephoenix888.savemylife.MessagePreferences
import kotlinx.coroutines.flow.Flow
import kotlin.time.Duration

interface MessageLocalDataSource {
    val preferences: Flow<MessagePreferences>

    suspend fun setMessageTemplate(newMessageTemplate: String)

    suspend fun setSendingInterval(newSendingInterval: Duration)
}