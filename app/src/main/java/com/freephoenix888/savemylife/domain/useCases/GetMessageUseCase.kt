package com.freephoenix888.savemylife.domain.useCases

import com.freephoenix888.savemylife.domain.models.PhoneNumber
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class GetMessageUseCase @Inject constructor(
    val getMessageTemplateFlowUseCase: GetMessageTemplateFlowUseCase,
    val getUserLocationUrlUseCase: GetUserLocationUrlUseCase
) {
    suspend operator fun invoke(phoneNumber: PhoneNumber): String {
        val message = getMessageTemplateFlowUseCase().first()
        return message.replace("{CONTACT_NAME}", phoneNumber.contactName)
            .replace("{LOCATION_URL}", getUserLocationUrlUseCase())
    }
}