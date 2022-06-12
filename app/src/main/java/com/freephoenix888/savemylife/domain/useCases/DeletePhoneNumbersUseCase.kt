package com.freephoenix888.savemylife.domain.useCases

import com.freephoenix888.savemylife.data.sources.interfaces.PhoneNumberLocalDataSource
import com.freephoenix888.savemylife.domain.models.PhoneNumber
import com.freephoenix888.savemylife.mappers.PhoneNumberToPhoneNumberEntityMapper
import javax.inject.Inject


class DeletePhoneNumbersUseCase @Inject constructor(
    val emergencyPhoneNumberLocalDataSource: PhoneNumberLocalDataSource,
    val contactPhoneNumberToPhoneNumberEntityMapper: PhoneNumberToPhoneNumberEntityMapper
) {
    suspend operator fun invoke(contactPhoneNumberList: List<PhoneNumber>): Int {
        return emergencyPhoneNumberLocalDataSource.deleteAll(
            contactPhoneNumberList.map {
                contactPhoneNumberToPhoneNumberEntityMapper.map(it)
            }
        )
    }
}