package com.freephoenix888.savemylife.mappers

import android.content.Context
import com.freephoenix888.savemylife.data.room.entities.PhoneNumberEntity
import com.freephoenix888.savemylife.domain.models.PhoneNumber
import com.freephoenix888.savemylife.domain.useCases.GetPhoneNumberByContentUriUseCase
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PhoneNumberEntityToPhoneNumberMapper @Inject constructor(@ApplicationContext val applicationContext: Context, val getPhoneNumberByContentUriUseCase: GetPhoneNumberByContentUriUseCase): Mapper<PhoneNumberEntity, PhoneNumber> {
    override fun map(input: PhoneNumberEntity): PhoneNumber {
        return getPhoneNumberByContentUriUseCase(
            context = applicationContext,
            contentUri = input.contentUri
        )
    }
}