package com.freephoenix888.savemylife.domain.useCases

import com.freephoenix888.savemylife.domain.models.ValidationResult
import com.freephoenix888.savemylife.domain.useCases.interfaces.ValidateInputUseCase
import javax.inject.Inject

class ValidateMessageTemplateInputUseCase @Inject constructor(): ValidateInputUseCase {
    override fun invoke(input: String): ValidationResult {
        return if (input.isEmpty()) {
            ValidationResult(false, "Message template must not be empty")
        } else {
            ValidationResult(true)
        }
    }
}