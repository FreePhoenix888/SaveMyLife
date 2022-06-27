package com.freephoenix888.savemylife.domain.models

data class ValidationResult(
    val isValid: Boolean,
    val errorMessage: String? = null
)