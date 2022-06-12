package com.freephoenix888.savemylife.domain.useCases

import com.freephoenix888.savemylife.data.repositories.PhoneNumberRepository
import com.freephoenix888.savemylife.domain.models.PhoneNumber
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPhoneNumberListFlowUseCase @Inject constructor(
    private val phoneNumberRepository: PhoneNumberRepository,
) {
    operator fun invoke(): Flow<List<PhoneNumber>> {
        return phoneNumberRepository.getPhoneNumberList()
    }

}
