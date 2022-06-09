package com.freephoenix888.savemylife.constants

import androidx.core.net.toUri
import com.freephoenix888.savemylife.domain.models.Contact
import com.freephoenix888.savemylife.domain.models.ContactWithPhoneNumbers

object EmergencyContactsConstants {
    val fakeContactsWithPhoneNumbers = listOf(
        ContactWithPhoneNumbers(
            contact = Contact(
                uri = "0".toUri(),
                name = "Name",
            ),
            phoneNumbers = listOf("+7 777 777 7777", "+7 777 777 7778", "+7 777 777 7779")
        ),
        ContactWithPhoneNumbers(
            contact = Contact(
                uri = "1".toUri(),
                name = "Name",
            ),
            phoneNumbers = listOf("+7 777 777 7788", "+7 777 777 7799", "+7 777 777 7700")
        )
    )
}