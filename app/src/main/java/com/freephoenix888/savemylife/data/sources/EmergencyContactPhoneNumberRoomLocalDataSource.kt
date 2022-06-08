package com.freephoenix888.savemylife.data.sources

import com.freephoenix888.savemylife.data.room.databases.ContactPhoneNumberDatabase
import com.freephoenix888.savemylife.data.room.entities.ContactPhoneNumberEntity
import com.freephoenix888.savemylife.data.sources.interfaces.EmergencyContactPhoneNumberLocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EmergencyContactPhoneNumberRoomLocalDataSource @Inject constructor(private val database: ContactPhoneNumberDatabase) :
    EmergencyContactPhoneNumberLocalDataSource {
    override val contactPhoneNumbers: Flow<List<ContactPhoneNumberEntity>> = database.dao().getContactPhoneNumbers()
    override suspend fun insert(contact: ContactPhoneNumberEntity) {
        database.dao().insert(contact)
    }

    override suspend fun delete(contact: ContactPhoneNumberEntity) {
        database.dao().delete(contact)
    }

    override suspend fun insertAll(contacts: List<ContactPhoneNumberEntity>): List<Long> {
        return database.dao().insertAll(contacts)
    }


    override suspend fun deleteAll(contacts: List<ContactPhoneNumberEntity>): Int {
        return database.dao().deleteAll(contacts)
    }

}