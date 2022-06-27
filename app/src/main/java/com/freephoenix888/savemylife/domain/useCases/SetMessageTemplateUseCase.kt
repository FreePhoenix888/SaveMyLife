package com.freephoenix888.savemylife.domain.useCases

import com.freephoenix888.savemylife.data.repositories.MessageRepository
import javax.inject.Inject

class SetMessageTemplateUseCase @Inject constructor(val messageRepository: MessageRepository) {
    suspend operator fun invoke(newMessageTemplate: String) {
        messageRepository.setTemplate(newMessageTemplate)
    }
}