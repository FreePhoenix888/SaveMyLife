package com.freephoenix888.savemylife.data.sources

import com.freephoenix888.savemylife.data.room.databases.PhoneNumberDatabase
import com.freephoenix888.savemylife.data.room.entities.PhoneNumberEntity
import com.freephoenix888.savemylife.data.sources.interfaces.PhoneNumberLocalDataSource
import com.freephoenix888.savemylife.domain.models.PhoneNumber
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhoneNumberRoomLocalDataSource @Inject constructor(private val database: PhoneNumberDatabase) :
    PhoneNumberLocalDataSource {

    override fun getPhoneNumberList(): Flow<List<PhoneNumberEntity>> {
        return database.dao().getPhoneNumberList()
    }

    override suspend fun insert(contactPhoneNumber: PhoneNumberEntity) {
        database.dao().insert(contactPhoneNumber)
    }

    override suspend fun delete(contactPhoneNumber: PhoneNumberEntity) {
        database.dao().delete(contactPhoneNumber)
    }

    override suspend fun insertAll(contactPhoneNumberList: List<PhoneNumberEntity>): List<Long> {
        return database.dao().insertAll(contactPhoneNumberList)
    }


    override suspend fun deleteAll(contactPhoneNumberList: List<PhoneNumberEntity>): Int {
        return database.dao().deleteAll(contactPhoneNumberList)
    }

}