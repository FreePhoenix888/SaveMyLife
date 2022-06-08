package com.freephoenix888.savemylife.domain.useCases

import com.freephoenix888.savemylife.data.repositories.EmergencyMessageRepository
import javax.inject.Inject

class SetEmergencyMessageTemplateUseCase @Inject constructor(val emergencyMessageRepository: EmergencyMessageRepository) {
    suspend operator fun invoke(newEmergencyMessageTemplate: String) {
        emergencyMessageRepository.setMessageTemplate(newEmergencyMessageTemplate)
    }
}