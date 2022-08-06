package com.freephoenix888.savemylife.domain.useCases

import com.freephoenix888.savemylife.data.repositories.MessageRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetIsMessageCommandsEnabledUseCase @Inject constructor(private val messageRepository: MessageRepository) {
    suspend operator fun invoke() {
        messageRepository.settings.map { it.isMessageCommandsEnabled }
    }
}