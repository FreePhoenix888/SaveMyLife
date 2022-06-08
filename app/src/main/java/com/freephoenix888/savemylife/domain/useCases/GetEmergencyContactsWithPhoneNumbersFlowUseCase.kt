package com.freephoenix888.savemylife.domain.useCases

import com.freephoenix888.savemylife.data.repositories.EmergencyContactRepository
import com.freephoenix888.savemylife.domain.models.ContactWithPhoneNumbers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEmergencyContactsWithPhoneNumbersFlowUseCase @Inject constructor(val repository: EmergencyContactRepository) {
    operator fun invoke(): Flow<List<ContactWithPhoneNumbers>>{
        return repository.contactsWithPhoneNumbers
    }
}