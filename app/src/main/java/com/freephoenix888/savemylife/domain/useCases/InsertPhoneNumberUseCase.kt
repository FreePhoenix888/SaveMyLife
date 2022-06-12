package com.freephoenix888.savemylife.domain.useCases

import com.freephoenix888.savemylife.data.sources.interfaces.PhoneNumberLocalDataSource
import com.freephoenix888.savemylife.domain.models.PhoneNumber
import com.freephoenix888.savemylife.mappers.PhoneNumberToPhoneNumberEntityMapper
import javax.inject.Inject

class InsertPhoneNumberUseCase @Inject constructor(
    val emergencyPhoneNumberLocalDataSource: PhoneNumberLocalDataSource,
    val contactPhoneNumberToPhoneNumberEntityMapper: PhoneNumberToPhoneNumberEntityMapper
) {
    suspend operator fun invoke(contactPhoneNumber: PhoneNumber) {
        return emergencyPhoneNumberLocalDataSource.insert(
            contactPhoneNumberToPhoneNumberEntityMapper.map(contactPhoneNumber)
        )
    }
}