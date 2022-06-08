package com.freephoenix888.savemylife.domain.useCases

import com.freephoenix888.savemylife.data.sources.interfaces.EmergencyContactPhoneNumberLocalDataSource
import com.freephoenix888.savemylife.domain.models.ContactPhoneNumber
import com.freephoenix888.savemylife.mappers.ContactPhoneNumberToContactPhoneNumberEntityMapper
import javax.inject.Inject


class DeleteEmergencyContactPhoneNumberUseCase @Inject constructor(
    val emergencyContactPhoneNumberLocalDataSource: EmergencyContactPhoneNumberLocalDataSource,
    val contactPhoneNumberToContactPhoneNumberEntityMapper: ContactPhoneNumberToContactPhoneNumberEntityMapper
) {
    suspend operator fun invoke(contactPhoneNumber: ContactPhoneNumber) {
        return emergencyContactPhoneNumberLocalDataSource.delete(
            contactPhoneNumberToContactPhoneNumberEntityMapper.map(contactPhoneNumber)
        )
    }
}