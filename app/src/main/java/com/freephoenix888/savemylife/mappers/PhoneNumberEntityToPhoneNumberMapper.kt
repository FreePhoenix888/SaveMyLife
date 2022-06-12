package com.freephoenix888.savemylife.mappers

import android.content.Context
import com.freephoenix888.savemylife.Utils
import com.freephoenix888.savemylife.data.room.entities.PhoneNumberEntity
import com.freephoenix888.savemylife.domain.models.PhoneNumber
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PhoneNumberEntityToPhoneNumberMapper @Inject constructor(@ApplicationContext val applicationContext: Context): Mapper<PhoneNumberEntity, PhoneNumber> {
    override fun map(input: PhoneNumberEntity): PhoneNumber {
        return Utils.getPhoneNumberByContentUri(
            context = applicationContext,
            contentUri = input.contentUri
        )
    }
}