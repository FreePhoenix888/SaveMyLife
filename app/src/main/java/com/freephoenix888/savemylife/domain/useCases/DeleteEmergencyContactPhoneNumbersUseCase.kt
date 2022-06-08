package com.freephoenix888.savemylife.domain.useCases

import com.freephoenix888.savemylife.data.sources.interfaces.EmergencyContactPhoneNumberLocalDataSource
import com.freephoenix888.savemylife.domain.models.ContactPhoneNumber
import com.freephoenix888.savemylife.mappers.ContactPhoneNumberToContactPhoneNumberEntityMapper
import javax.inject.Inject


class DeleteEmergencyContactPhoneNumbersUseCase @Inject constructor(
    val emergencyContactPhoneNumberLocalDataSource: EmergencyContactPhoneNumberLocalDataSource,
    val contactPhoneNumberToContactPhoneNumberEntityMapper: ContactPhoneNumberToContactPhoneNumberEntityMapper
) {
    suspend operator fun invoke(contactPhoneNumberList: List<ContactPhoneNumber>): Int {
        return emergencyContactPhoneNumberLocalDataSource.deleteAll(
            contactPhoneNumberList.map {
                contactPhoneNumberToContactPhoneNumberEntityMapper.map(it)
            }
        )
    }
}