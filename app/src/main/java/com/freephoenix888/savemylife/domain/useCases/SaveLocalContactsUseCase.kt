package com.freephoenix888.savemylife.domain.useCases

import com.freephoenix888.savemylife.ui.states.ContactsItemUiState
import com.freephoenix888.savemylife.data.repositories.ContactRepository
import com.freephoenix888.savemylife.data.room.databases.entities.ContactEntity
import com.freephoenix888.savemylife.domain.useCases.interfaces.SaveContactsUseCase

class SaveLocalContactsUseCase(val contactRepository: ContactRepository) :
    SaveContactsUseCase {
    override suspend operator fun invoke(vararg contacts: ContactsItemUiState): List<Long>{
        val contactEntities = contacts.map {contact: ContactsItemUiState ->
            ContactEntity(
                uri = contact.uri
            )
        }
        return contactRepository.insert(contacts = contactEntities.toTypedArray())
    }
}