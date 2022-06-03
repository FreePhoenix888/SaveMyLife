package com.freephoenix888.savemylife.domain.useCases

import com.freephoenix888.savemylife.data.repositories.EmergencyMessageRepository
import com.freephoenix888.savemylife.domain.models.EmergencyMessageData
import javax.inject.Inject

class GetEmergencyMessageDataUseCase @Inject constructor(val emergencyMessageRepository: EmergencyMessageRepository) {
    operator fun invoke(): EmergencyMessageData {
        return EmergencyMessageData(
            messageTemplate = emergencyMessageRepository.messageTemplate,
            sendingIntervalInSeconds = emergencyMessageRepository.sendingInterval
        )
    }
}