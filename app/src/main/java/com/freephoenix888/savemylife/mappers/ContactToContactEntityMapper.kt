package com.freephoenix888.savemylife.mappers

import com.freephoenix888.savemylife.domain.models.Contact
import javax.inject.Inject

class ContactToContactEntityMapper @Inject constructor(): Mapper<Contact, com.freephoenix888.savemylife.data.room.entities.ContactEntity> {
    override fun map(input: Contact): com.freephoenix888.savemylife.data.room.entities.ContactEntity {
        return com.freephoenix888.savemylife.data.room.entities.ContactEntity(uri = input.uri, name = input.name)
    }
}