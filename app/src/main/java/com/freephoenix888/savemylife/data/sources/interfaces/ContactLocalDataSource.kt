package com.freephoenix888.savemylife.data.sources.interfaces

import com.freephoenix888.savemylife.data.room.entities.ContactEntity
import com.freephoenix888.savemylife.data.room.entities.relations.ContactWithPhoneNumbersRelation
import kotlinx.coroutines.flow.Flow

interface ContactLocalDataSource {
    val contactsWithPhoneNumbers: Flow<List<ContactWithPhoneNumbersRelation>>

    suspend fun insert(contact: ContactEntity)

    suspend fun insertList(contacts: List<ContactEntity>): List<Long>

    suspend fun delete(contact: ContactEntity)

    suspend fun deleteList(contacts: List<ContactEntity>): Int

}