package com.freephoenix888.savemylife.domain.useCases

import com.freephoenix888.savemylife.data.repositories.EmergencyContactRepository
import com.freephoenix888.savemylife.domain.models.Contact
import javax.inject.Inject

class InsertEmergencyContactUseCase @Inject constructor(val repository: EmergencyContactRepository) {
    suspend operator fun invoke(contact: Contact){
        return repository.insert(contact = contact)
    }
}