package com.freephoenix888.savemylife.domain.useCases

import com.freephoenix888.savemylife.domain.models.PhoneNumber
import kotlinx.coroutines.flow.last
import javax.inject.Inject

class GetMessageUseCase @Inject constructor(
    val getMessageDataUseCase: GetMessageDataUseCase,
    val getUserLocationUrlUseCase: GetUserLocationUrlUseCase
) {
    suspend operator fun invoke(phoneNumber: PhoneNumber): String {
        val message = getMessageDataUseCase()
        return message.messageTemplate.last().replace("{CONTACT_NAME}", phoneNumber.contactName)
            .replace("{LOCATION_URL}", getUserLocationUrlUseCase())
    }
}