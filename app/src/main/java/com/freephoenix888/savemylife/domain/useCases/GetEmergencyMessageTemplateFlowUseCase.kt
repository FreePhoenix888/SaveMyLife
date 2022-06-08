package com.freephoenix888.savemylife.domain.useCases

import com.freephoenix888.savemylife.data.repositories.EmergencyMessageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEmergencyMessageTemplateFlowUseCase @Inject constructor (val emergencyMessageRepository: EmergencyMessageRepository) {
    operator fun invoke(): Flow<String> {
        return emergencyMessageRepository.messageTemplate
    }
}