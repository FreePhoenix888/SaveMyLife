package com.freephoenix888.savemylife.mappers

import com.freephoenix888.savemylife.data.room.entities.Contact
import com.freephoenix888.savemylife.data.room.entities.ContactPhoneNumberEntity
import com.freephoenix888.savemylife.domain.models.ContactWithPhoneNumbers
import javax.inject.Inject

class ContactWithPhoneNumbersToDataContactWithPhoneNumbersMapper @Inject constructor(): Mapper<ContactWithPhoneNumbers, com.freephoenix888.savemylife.data.room.entities.relations.ContactWithPhoneNumbers> {
    override fun map(input: ContactWithPhoneNumbers): com.freephoenix888.savemylife.data.room.entities.relations.ContactWithPhoneNumbers {
        return com.freephoenix888.savemylife.data.room.entities.relations.ContactWithPhoneNumbers(
            contact = Contact(
                uri = input.contact.uri,
                name = input.contact.name
            ),
            phoneNumbers = input.phoneNumbers.map {
                ContactPhoneNumberEntity(
                    phoneNumber = it,
                    contactUri = input.contact.uri
                )
            }
        )
    }
}