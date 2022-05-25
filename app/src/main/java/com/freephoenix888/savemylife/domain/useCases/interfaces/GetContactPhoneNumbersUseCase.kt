package com.freephoenix888.savemylife.domain.useCases.interfaces

import android.content.Context
import com.freephoenix888.savemylife.data.models.PhoneNumber

interface GetContactPhoneNumbersUseCase {
    operator fun invoke(id: String): List<PhoneNumber>
}