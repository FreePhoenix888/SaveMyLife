package com.freephoenix888.savemylife.mappers

import com.freephoenix888.savemylife.data.room.entities.Contact
import com.freephoenix888.savemylife.domain.models.Contact
import javax.inject.Inject

class ContactEntityToContactMapper @Inject constructor(): Mapper<com.freephoenix888.savemylife.data.room.entities.Contact, Contact> {
    override fun map(input: com.freephoenix888.savemylife.data.room.entities.Contact): Contact {
        return Contact(uri = input.uri, name = input.name, listOf() )
    }
}