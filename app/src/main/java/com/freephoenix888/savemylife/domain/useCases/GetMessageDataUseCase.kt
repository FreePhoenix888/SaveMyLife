package com.freephoenix888.savemylife.domain.useCases

import com.freephoenix888.savemylife.data.repositories.MessageRepository
import com.freephoenix888.savemylife.domain.models.MessageData
import javax.inject.Inject

class GetMessageDataUseCase @Inject constructor(val messageRepository: MessageRepository) {
    operator fun invoke(): MessageData {
        return MessageData(
            messageTemplate = messageRepository.messageTemplate,
            sendingIntervalInSeconds = messageRepository.sendingInterval
        )
    }
}