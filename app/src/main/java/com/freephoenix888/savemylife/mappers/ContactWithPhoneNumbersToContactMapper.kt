package com.freephoenix888.savemylife.mappers

import com.freephoenix888.savemylife.data.room.entities.relations.ContactWithPhoneNumbers
import com.freephoenix888.savemylife.domain.models.Contact
import javax.inject.Inject

class ContactWithPhoneNumbersToContactMapper @Inject constructor(): Mapper<ContactWithPhoneNumbers, Contact> {
    override fun map(input: ContactWithPhoneNumbers): Contact {
        return Contact(uri = input.contact.uri, name = input.contact.name, phoneNumbers = input.phoneNumbers.map { it.phoneNumber })
    }
}