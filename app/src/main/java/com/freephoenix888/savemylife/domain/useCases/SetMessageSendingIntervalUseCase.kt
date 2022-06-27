package com.freephoenix888.savemylife.domain.useCases

import com.freephoenix888.savemylife.data.repositories.MessageRepository
import javax.inject.Inject
import kotlin.time.Duration

class SetMessageSendingIntervalUseCase @Inject constructor (val messageRepository: MessageRepository) {
    suspend operator fun invoke(newSendingInterval: Duration) {
        messageRepository.setSendingInterval(newSendingInterval)
    }
}