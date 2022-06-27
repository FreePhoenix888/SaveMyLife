package com.freephoenix888.savemylife.domain.useCases

import com.freephoenix888.savemylife.data.repositories.MessageRepository
import com.freephoenix888.savemylife.domain.models.MessageSettings
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMessageSettingsFlowUseCase @Inject constructor(val messageRepository: MessageRepository) {
    operator fun invoke(): Flow<MessageSettings> {
        return messageRepository.settings
    }
}