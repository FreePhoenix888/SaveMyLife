package com.freephoenix888.savemylife.data.repositories

import com.freephoenix888.savemylife.data.room.entities.ContactPhoneNumberEntity
import com.freephoenix888.savemylife.data.sources.interfaces.EmergencyContactPhoneNumberLocalDataSource
import com.freephoenix888.savemylife.domain.models.ContactPhoneNumber
import com.freephoenix888.savemylife.mappers.ContactPhoneNumberToContactPhoneNumberEntityMapper
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContactPhoneNumberRepository @Inject constructor(
    private val contactPhoneNumberLocalDataSource: EmergencyContactPhoneNumberLocalDataSource,
    private val contactPhoneNumberToContactPhoneNumberEntityMapper: ContactPhoneNumberToContactPhoneNumberEntityMapper
) {
    val contactPhoneNumbers: Flow<List<ContactPhoneNumberEntity>> =
        contactPhoneNumberLocalDataSource.contactPhoneNumbers

    suspend fun insert(contactPhoneNumber: ContactPhoneNumber) {
        return contactPhoneNumberLocalDataSource.insert(
            contactPhoneNumberToContactPhoneNumberEntityMapper.map(contactPhoneNumber)
        )
    }

    suspend fun insertAll(contactPhoneNumbers: List<ContactPhoneNumber>): List<Long> {
        return contactPhoneNumberLocalDataSource.insertAll(contactPhoneNumbers.map {
            contactPhoneNumberToContactPhoneNumberEntityMapper.map(it)
        })
    }

    suspend fun delete(contactPhoneNumber: ContactPhoneNumber) {
        return contactPhoneNumberLocalDataSource.delete(
            contactPhoneNumberToContactPhoneNumberEntityMapper.map(contactPhoneNumber)
        )
    }

    suspend fun deleteAll(contactPhoneNumbers: List<ContactPhoneNumber>): Int {
        return contactPhoneNumberLocalDataSource.deleteAll(contactPhoneNumbers.map {
            contactPhoneNumberToContactPhoneNumberEntityMapper.map(it)
        })
    }
}