package com.freephoenix888.savemylife.mappers

import com.freephoenix888.savemylife.data.room.entities.ContactPhoneNumberEntity
import com.freephoenix888.savemylife.domain.models.ContactPhoneNumber
import javax.inject.Inject

class ContactPhoneNumberEntityToContactPhoneNumberMapper @Inject constructor(): Mapper<ContactPhoneNumberEntity, ContactPhoneNumber> {
    override fun map(input: ContactPhoneNumberEntity): ContactPhoneNumber {
        return ContactPhoneNumber(
            contactUri = input.contactUri,
            phoneNumber = input.phoneNumber
        )
    }
}