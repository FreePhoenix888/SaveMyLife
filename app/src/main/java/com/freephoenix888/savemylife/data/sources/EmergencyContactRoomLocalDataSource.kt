package com.freephoenix888.savemylife.data.sources

import com.freephoenix888.savemylife.data.room.databases.ContactDatabase
import com.freephoenix888.savemylife.data.room.entities.ContactEntity
import com.freephoenix888.savemylife.data.room.entities.relations.ContactWithPhoneNumbersRelation
import com.freephoenix888.savemylife.data.sources.interfaces.ContactLocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EmergencyContactRoomLocalDataSource @Inject constructor (private val database: ContactDatabase):
    ContactLocalDataSource {
    override val contactsWithPhoneNumbers: Flow<List<ContactWithPhoneNumbersRelation>> = database.dao().getContactsWithPhoneNumbers()

    override suspend fun insert(contact: ContactEntity) {
        return database.dao().insert(contact)
    }

    override suspend fun insertList(contacts: List<ContactEntity>): List<Long> {
        return database.dao().insertList(contacts)
    }

    override suspend fun delete(contact: ContactEntity) {
        return database.dao().delete(contact)
    }

    override suspend fun deleteList(contacts: List<ContactEntity>): Int {
        return database.dao().deleteList(contacts)
    }

}