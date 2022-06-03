package com.freephoenix888.savemylife.domain.useCases

import com.freephoenix888.savemylife.data.repositories.EmergencyContactRepository
import com.freephoenix888.savemylife.data.room.databases.entities.ContactEntity
import com.freephoenix888.savemylife.domain.models.Contact
import javax.inject.Inject

class InsertEmergencyContactsUseCase @Inject constructor(val repository: EmergencyContactRepository) {
    suspend operator fun invoke(vararg contacts: Contact): List<Long> {
        return repository.insert(contacts = contacts)
    }
}