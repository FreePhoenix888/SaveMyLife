package com.freephoenix888.savemylife.domain.useCases

import com.freephoenix888.savemylife.domain.models.ValidationResult
import javax.inject.Inject

class ValidateMessageSendingIntervalInputUseCase @Inject constructor() {
    operator fun invoke(input: String): ValidationResult {
        if (input.isEmpty()) {
            return ValidationResult.Error(
                "Message sending interval must not be empty"
            )
        }
        val messageSendingInterval = input.toLongOrNull()
            ?: return ValidationResult.Error(
                "Message sending interval must be a number"
            )
        if (messageSendingInterval < 1) {
            return ValidationResult.Error(
                "Message sending interval must be greater than 0"
            )
        }
        return ValidationResult.Success
    }
}