package com.freephoenix888.savemylife.data.sources.interfaces

import com.freephoenix888.savemylife.data.room.entities.PhoneNumberEntity
import kotlinx.coroutines.flow.Flow

interface PhoneNumberLocalDataSource {
    fun getPhoneNumberList(): Flow<List<PhoneNumberEntity>>

    suspend fun insert(contactPhoneNumber: PhoneNumberEntity)

    suspend fun delete(contactPhoneNumber: PhoneNumberEntity)

    suspend fun insertAll(contactPhoneNumberList: List<PhoneNumberEntity>): List<Long>

    suspend fun deleteAll(contactPhoneNumberList: List<PhoneNumberEntity>): Int
}