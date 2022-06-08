package com.freephoenix888.savemylife.domain.useCases

import com.freephoenix888.savemylife.SecondsInterval
import com.freephoenix888.savemylife.data.repositories.EmergencyMessageRepository
import javax.inject.Inject

class SetEmergencyMessageSendingIntervalUseCase @Inject constructor (val emergencyMessageRepository: EmergencyMessageRepository) {
    suspend operator fun invoke(newSendingInterval: SecondsInterval) {
        emergencyMessageRepository.setSendingInterval(newSendingInterval)
    }
}