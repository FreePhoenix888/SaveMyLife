package com.freephoenix888.savemylife.data.sources.interfaces

import com.freephoenix888.savemylife.SecondsInterval
import kotlinx.coroutines.flow.Flow

interface MessageLocalDataSource {
    val messageTemplate: Flow<String>
    suspend fun setMessageTemplate(newMessageTemplate: String)

    val sendingInterval: Flow<SecondsInterval>
    suspend fun setSendingInterval(newSendingInterval: SecondsInterval)
}