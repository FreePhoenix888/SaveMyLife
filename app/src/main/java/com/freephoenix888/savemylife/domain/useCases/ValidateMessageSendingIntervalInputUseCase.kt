package com.freephoenix888.savemylife.domain.useCases

import com.freephoenix888.savemylife.domain.models.ValidationResult
import com.freephoenix888.savemylife.domain.useCases.interfaces.ValidateInputUseCase
import javax.inject.Inject

class ValidateMessageSendingIntervalInputUseCase @Inject constructor() : ValidateInputUseCase {
    override operator fun invoke(input: String): ValidationResult {
        if (input.isEmpty()) {
            return ValidationResult(
                isValid = false,
                errorMessage = "Message sending interval must not be empty"
            )
        }
        val messageSendingInterval = input.toLongOrNull()
            ?: return ValidationResult(
                isValid = false,
                errorMessage = "Message sending interval must be a number"
            )
        if (messageSendingInterval < 1) {
            return ValidationResult(
                isValid = false,
                errorMessage = "Message sending interval must be greater than 0"
            )
        }
        return ValidationResult(
            isValid = true
        )
    }
}