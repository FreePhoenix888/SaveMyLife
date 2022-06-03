package com.freephoenix888.savemylife.data.repositories

import com.freephoenix888.savemylife.SecondsInterval
import com.freephoenix888.savemylife.data.sources.interfaces.EmergencyMessageLocalDataSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EmergencyMessageRepository @Inject constructor(val localStorage: EmergencyMessageLocalDataSource) {
    val messageTemplate = localStorage.messageTemplate
    suspend fun setMessageTemplate(newMessageTemplate: String) {
        localStorage.setMessageTemplate(newMessageTemplate)
    }

    val sendingInterval = localStorage.sendingInterval
    suspend fun setSendingInterval(newSendingInterval: SecondsInterval) {
        localStorage.setSendingInterval(newSendingInterval)
    }
}
