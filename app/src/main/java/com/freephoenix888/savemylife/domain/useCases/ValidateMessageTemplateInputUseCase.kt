package com.freephoenix888.savemylife.domain.useCases

import com.freephoenix888.savemylife.domain.models.ValidationResult
import javax.inject.Inject

class ValidateMessageTemplateInputUseCase @Inject constructor(val getIsLocationSharingEnabledUseCase: SetIsLocationSharingEnabledUseCase) {
    operator fun invoke(input: String): ValidationResult {
        return if (input.isEmpty()) {
            ValidationResult.Error("Message template must not be empty")
        } else if (isLocationSharingEnabledUseCase()){

        }
        else {
            ValidationResult.Success
        }
    }
}