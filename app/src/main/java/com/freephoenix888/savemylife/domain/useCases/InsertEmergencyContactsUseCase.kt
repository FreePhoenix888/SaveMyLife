package com.freephoenix888.savemylife.domain.useCases

import com.freephoenix888.savemylife.data.repositories.EmergencyContactRepository
import com.freephoenix888.savemylife.domain.models.Contact
import javax.inject.Inject

class InsertEmergencyContactsUseCase @Inject constructor(val repository: EmergencyContactRepository) {
    suspend operator fun invoke(contacts: List<Contact>): List<Long> {
        return repository.insertList(contacts = contacts)
    }
}