package com.freephoenix888.savemylife.mappers

import com.freephoenix888.savemylife.data.room.entities.relations.ContactWithPhoneNumbersRelation
import com.freephoenix888.savemylife.domain.models.Contact
import com.freephoenix888.savemylife.domain.models.ContactWithPhoneNumbers
import javax.inject.Inject

class ContactWithPhoneNumbersRelationToContactWithPhoneNumbersMapper @Inject constructor() :
    Mapper<ContactWithPhoneNumbersRelation, ContactWithPhoneNumbers> {
    override fun map(input: ContactWithPhoneNumbersRelation): ContactWithPhoneNumbers {
        return ContactWithPhoneNumbers(
            contact = Contact(
                uri = input.contact.uri,
                name = input.contact.name
            ), phoneNumbers = input.phoneNumbers.map { it.phoneNumber })
    }
}