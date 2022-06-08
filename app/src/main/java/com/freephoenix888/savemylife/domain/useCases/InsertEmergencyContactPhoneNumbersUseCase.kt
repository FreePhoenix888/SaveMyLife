package com.freephoenix888.savemylife.domain.useCases

import com.freephoenix888.savemylife.data.sources.interfaces.EmergencyContactPhoneNumberLocalDataSource
import com.freephoenix888.savemylife.domain.models.ContactPhoneNumber
import com.freephoenix888.savemylife.mappers.ContactPhoneNumberToContactPhoneNumberEntityMapper
import javax.inject.Inject

class InsertEmergencyContactPhoneNumbersUseCase @Inject constructor(
    val emergencyContactPhoneNumberLocalDataSource: EmergencyContactPhoneNumberLocalDataSource,
    val contactPhoneNumberToContactPhoneNumberEntityMapper: ContactPhoneNumberToContactPhoneNumberEntityMapper
) {
    suspend operator fun invoke(contactPhoneNumberList: List<ContactPhoneNumber>): List<Long> {
        return emergencyContactPhoneNumberLocalDataSource.insertAll(
            contactPhoneNumberList.map {
                contactPhoneNumberToContactPhoneNumberEntityMapper.map(it)
            }
        )
    }
}