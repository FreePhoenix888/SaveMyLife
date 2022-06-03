package com.freephoenix888.savemylife.domain.useCases

import com.freephoenix888.savemylife.domain.models.Contact
import kotlinx.coroutines.flow.last
import javax.inject.Inject

class GetEmergencyMessageUseCase @Inject constructor(
    val getEmergencyMessageDataUseCase: GetEmergencyMessageDataUseCase,
    val getUserLocationUrlUseCase: GetUserLocationUrlUseCase
) {
    suspend operator fun invoke(contact: Contact): String {
        val message = getEmergencyMessageDataUseCase()
        return message.messageTemplate.last().replace("{CONTACT_NAME}", contact.name)
            .replace("{LOCATION_URL}", getUserLocationUrlUseCase())
    }
}