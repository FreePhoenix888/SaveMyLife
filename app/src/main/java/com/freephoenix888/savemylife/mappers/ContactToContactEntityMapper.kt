package com.freephoenix888.savemylife.mappers

import com.freephoenix888.savemylife.data.room.entities.Contact
import com.freephoenix888.savemylife.domain.models.Contact
import javax.inject.Inject

class ContactToContactEntityMapper @Inject constructor(): Mapper<Contact, com.freephoenix888.savemylife.data.room.entities.Contact> {
    override fun map(input: Contact): com.freephoenix888.savemylife.data.room.entities.Contact {
        return com.freephoenix888.savemylife.data.room.entities.Contact(uri = input.uri, name = input.name)
    }
}