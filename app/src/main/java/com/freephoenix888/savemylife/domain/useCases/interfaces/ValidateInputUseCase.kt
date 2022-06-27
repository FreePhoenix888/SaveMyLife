package com.freephoenix888.savemylife.domain.useCases.interfaces

import com.freephoenix888.savemylife.domain.models.ValidationResult

interface ValidateInputUseCase {
    operator fun invoke(input: String): ValidationResult
}