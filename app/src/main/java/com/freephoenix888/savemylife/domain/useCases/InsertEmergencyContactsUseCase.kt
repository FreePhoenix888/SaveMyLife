package com.freephoenix888.savemylife.domain.useCases

import com.freephoenix888.savemylife.domain.models.Contact
import com.freephoenix888.savemylife.data.repositories.EmergencyContactRepository
import com.freephoenix888.savemylife.data.room.databases.entities.ContactEntity
import javax.inject.Inject

class InsertEmergencyContactsUseCase @Inject constructor(val repository: EmergencyContactRepository){
    suspend operator fun invoke(vararg contacts: Contact): List<Long>{
        val contactEntities = contacts.map {contact: Contact ->
            ContactEntity(
                uri = contact.uri
            )
        }
        return repository.insert(contacts = contactEntities.toTypedArray())
    }
}