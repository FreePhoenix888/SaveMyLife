package com.freephoenix888.savemylife.mappers

import com.freephoenix888.savemylife.data.room.entities.PhoneNumberEntity
import com.freephoenix888.savemylife.domain.models.PhoneNumber
import javax.inject.Inject

class PhoneNumberToPhoneNumberEntityMapper @Inject constructor(): Mapper<PhoneNumber, PhoneNumberEntity> {
    override fun map(input: PhoneNumber): PhoneNumberEntity {
        return PhoneNumberEntity(
           contentUri = input.contentUri,
        )
    }
}