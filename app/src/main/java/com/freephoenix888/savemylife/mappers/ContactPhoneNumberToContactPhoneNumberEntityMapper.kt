package com.freephoenix888.savemylife.mappers

import com.freephoenix888.savemylife.data.room.entities.ContactPhoneNumberEntity
import com.freephoenix888.savemylife.domain.models.ContactPhoneNumber
import javax.inject.Inject

class ContactPhoneNumberToContactPhoneNumberEntityMapper @Inject constructor(): Mapper<ContactPhoneNumber, ContactPhoneNumberEntity> {
    override fun map(input: ContactPhoneNumber): ContactPhoneNumberEntity {
        return ContactPhoneNumberEntity(
            phoneNumber = input.phoneNumber,
            contactUri = input.contactUri
        )
    }
}