package com.freephoenix888.savemylife.domain.useCases

import com.freephoenix888.savemylife.domain.models.Contact
import com.freephoenix888.savemylife.data.repositories.EmergencyContactRepository
import com.freephoenix888.savemylife.data.room.databases.entities.ContactEntity
import javax.inject.Inject

class DeleteEmergencyContactsUseCase @Inject constructor(val repository: EmergencyContactRepository){
    suspend operator fun invoke(vararg contacts: Contact): Int {
        val contactEntities = contacts.map { contact: Contact ->
            ContactEntity(uri = contact.uri)
        }
        return repository.delete(contacts = contactEntities.toTypedArray())
    }
}