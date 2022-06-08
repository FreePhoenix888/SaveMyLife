package com.freephoenix888.savemylife.data.sources.interfaces

import com.freephoenix888.savemylife.data.room.entities.ContactPhoneNumberEntity
import kotlinx.coroutines.flow.Flow

interface EmergencyContactPhoneNumberLocalDataSource {
    val contactPhoneNumbers: Flow<List<ContactPhoneNumberEntity>>

    suspend fun insert(contactPhoneNumber: ContactPhoneNumberEntity)

    suspend fun delete(contactPhoneNumber: ContactPhoneNumberEntity)

    suspend fun insertAll(contactPhoneNumber: List<ContactPhoneNumberEntity>): List<Long>

    suspend fun deleteAll(contactPhoneNumber: List<ContactPhoneNumberEntity>): Int
}