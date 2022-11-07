package com.freephoenix888.savemylife.domain.useCases

import com.freephoenix888.savemylife.constants.MessageTemplateVariables
import com.freephoenix888.savemylife.domain.models.ValidationResult
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ValidateMessageTemplateInputUseCase @Inject constructor(val getIsLocationSharingEnabledFlowUseCase: GetIsLocationSharingEnabledFlowUseCase) {
    suspend operator fun invoke(input: String): ValidationResult {
        return if (input.isEmpty()) {
            ValidationResult.Error("Message template must not be empty")
        } else if (input.contains("{${MessageTemplateVariables.LOCATION_URL.name}}") && !getIsLocationSharingEnabledFlowUseCase().first()){
            ValidationResult.Error("Message template cannot contain {LOCATION_URL} if location sharing is disabled")
        }
        else {
            ValidationResult.Success
        }
    }
}