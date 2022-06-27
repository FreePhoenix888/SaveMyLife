package com.freephoenix888.savemylife.domain.useCases

import com.freephoenix888.savemylife.data.repositories.MessageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.time.Duration

class GetMessageSendingIntervalUseCase @Inject constructor (val messageRepository: MessageRepository) {
    operator fun invoke(): Flow<Duration> {
        return messageRepository.settings.map {
            it.sendingInterval
        }
    }
}