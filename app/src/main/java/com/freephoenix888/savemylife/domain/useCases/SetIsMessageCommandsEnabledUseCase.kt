package com.freephoenix888.savemylife.domain.useCases

import com.freephoenix888.savemylife.data.repositories.MessageRepository
import javax.inject.Inject

class SetIsMessageCommandsEnabledUseCase @Inject constructor(private val messageRepository: MessageRepository) {
    suspend operator fun invoke(newIsMessageCommaEnabled: Boolean) {
        messageRepository.setIsMessageCommandsEnabled(newIsMessageCommaEnabled)
    }
}