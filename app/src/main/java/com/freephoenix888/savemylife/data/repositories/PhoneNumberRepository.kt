package com.freephoenix888.savemylife.data.repositories

import com.freephoenix888.savemylife.data.room.entities.PhoneNumberEntity
import com.freephoenix888.savemylife.data.sources.interfaces.PhoneNumberLocalDataSource
import com.freephoenix888.savemylife.domain.models.PhoneNumber
import com.freephoenix888.savemylife.mappers.PhoneNumberEntityToPhoneNumberMapper
import com.freephoenix888.savemylife.mappers.PhoneNumberToPhoneNumberEntityMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhoneNumberRepository @Inject constructor(
    private val contactPhoneNumberLocalDataSource: PhoneNumberLocalDataSource,
    private val contactPhoneNumberToPhoneNumberEntityMapper: PhoneNumberToPhoneNumberEntityMapper,
    private val contactPhoneNumberEntityToPhoneNumberMapper: PhoneNumberEntityToPhoneNumberMapper
) {
    fun getPhoneNumberList(): Flow<List<PhoneNumber>> {
        return contactPhoneNumberLocalDataSource.getPhoneNumberList()
            .map { phoneNumberEntityList: List<PhoneNumberEntity> ->
                phoneNumberEntityList.map { contactPhoneNumberEntity ->
                    contactPhoneNumberEntityToPhoneNumberMapper.map(contactPhoneNumberEntity)
                }
            }
    }

    suspend fun insert(contactPhoneNumber: PhoneNumber) {
        return contactPhoneNumberLocalDataSource.insert(
            contactPhoneNumberToPhoneNumberEntityMapper.map(contactPhoneNumber)
        )
    }

    suspend fun insertAll(contactPhoneNumbers: List<PhoneNumber>): List<Long> {
        return contactPhoneNumberLocalDataSource.insertAll(contactPhoneNumbers.map {
            contactPhoneNumberToPhoneNumberEntityMapper.map(it)
        })
    }

    suspend fun delete(contactPhoneNumber: PhoneNumber) {
        return contactPhoneNumberLocalDataSource.delete(
            contactPhoneNumberToPhoneNumberEntityMapper.map(contactPhoneNumber)
        )
    }

    suspend fun deleteAll(contactPhoneNumbers: List<PhoneNumber>): Int {
        return contactPhoneNumberLocalDataSource.deleteAll(contactPhoneNumbers.map {
            contactPhoneNumberToPhoneNumberEntityMapper.map(it)
        })
    }
}