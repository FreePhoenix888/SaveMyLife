package com.freephoenix888.savemylife.data.room.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.freephoenix888.savemylife.data.room.entities.ContactEntity
import com.freephoenix888.savemylife.data.room.entities.ContactPhoneNumberEntity

data class ContactWithPhoneNumbersRelation(
    @Embedded val contact: ContactEntity,
    @Relation(
        parentColumn = "uri",
        entityColumn = "contact_uri"
    ) val phoneNumbers: List<ContactPhoneNumberEntity>
)
