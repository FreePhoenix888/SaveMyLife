package com.freephoenix888.savemylife.domain.useCases

import android.util.Log
import com.freephoenix888.savemylife.constants.MessageConstants
import com.freephoenix888.savemylife.constants.MessageTemplateVariables
import com.freephoenix888.savemylife.domain.models.PhoneNumber
import com.freephoenix888.savemylife.enums.MessageCommand
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetMessageUseCase @Inject constructor(
    val getMessageTemplateFlowUseCase: GetMessageTemplateFlowUseCase,
    val getUserLocationUrlUseCase: GetUserLocationUrlUseCase
) {
    suspend operator fun invoke(phoneNumber: PhoneNumber): String {
        val message = getMessageTemplateFlowUseCase().first()
        return message.replace("{${MessageTemplateVariables.CONTACT_NAME.name}}", phoneNumber.contactName)
            .replace("{${MessageTemplateVariables.LOCATION_URL.name}}", getUserLocationUrlUseCase())
            .replace("{${MessageTemplateVariables.MESSAGE_COMMANDS.name}}", MessageCommand.values().joinToString {
                return@joinToString "${it.name} - ${it.description}"
            })
    }
}