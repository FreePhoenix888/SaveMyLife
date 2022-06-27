package com.freephoenix888.savemylife.domain.useCases

import com.freephoenix888.savemylife.data.repositories.MessageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetMessageTemplateFlowUseCase @Inject constructor (val messageRepository: MessageRepository) {
    operator fun invoke(): Flow<String> {
        return messageRepository.settings.map {
            it.template
        }
    }
}