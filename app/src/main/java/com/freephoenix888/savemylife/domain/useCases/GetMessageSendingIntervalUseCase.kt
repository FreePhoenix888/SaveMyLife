package com.freephoenix888.savemylife.domain.useCases

import com.freephoenix888.savemylife.SecondsInterval
import com.freephoenix888.savemylife.data.repositories.MessageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMessageSendingIntervalUseCase @Inject constructor (val messageRepository: MessageRepository) {
    operator fun invoke(): Flow<SecondsInterval> {
        return messageRepository.sendingInterval
    }
}