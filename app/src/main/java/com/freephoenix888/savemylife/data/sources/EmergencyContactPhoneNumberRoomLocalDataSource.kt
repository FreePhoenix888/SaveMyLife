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
    override suspend fun insert(contactPhoneNumber: ContactPhoneNumberEntity) {
        database.dao().insert(contactPhoneNumber)
    }

    override suspend fun delete(contactPhoneNumber: ContactPhoneNumberEntity) {
        database.dao().delete(contactPhoneNumber)
    }

    override suspend fun insertAll(contactPhoneNumberList: List<ContactPhoneNumberEntity>): List<Long> {
        return database.dao().insertAll(contactPhoneNumberList)
    }


    override suspend fun deleteAll(contactPhoneNumberList: List<ContactPhoneNumberEntity>): Int {
        return database.dao().deleteAll(contactPhoneNumberList)
    }

}