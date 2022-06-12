package com.freephoenix888.savemylife.domain.useCases

import com.freephoenix888.savemylife.SecondsInterval
import com.freephoenix888.savemylife.data.repositories.MessageRepository
import javax.inject.Inject

class SetMessageSendingIntervalUseCase @Inject constructor (val messageRepository: MessageRepository) {
    suspend operator fun invoke(newSendingInterval: SecondsInterval) {
        messageRepository.setSendingInterval(newSendingInterval)
    }
}