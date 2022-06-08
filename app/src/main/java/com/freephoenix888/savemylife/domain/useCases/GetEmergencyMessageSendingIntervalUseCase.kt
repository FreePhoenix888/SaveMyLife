package com.freephoenix888.savemylife.domain.useCases

import com.freephoenix888.savemylife.SecondsInterval
import com.freephoenix888.savemylife.data.repositories.EmergencyMessageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEmergencyMessageSendingIntervalUseCase @Inject constructor (val emergencyMessageRepository: EmergencyMessageRepository) {
    operator fun invoke(): Flow<SecondsInterval> {
        return emergencyMessageRepository.sendingInterval
    }
}