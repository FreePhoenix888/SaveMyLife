package com.freephoenix888.savemylife.domain.useCases

import com.freephoenix888.savemylife.ui.states.ContactsItemUiState
import com.freephoenix888.savemylife.data.repositories.ContactRepository
import com.freephoenix888.savemylife.data.room.databases.entities.ContactEntity
import com.freephoenix888.savemylife.domain.useCases.interfaces.DeleteContactsUseCase
import javax.inject.Inject

class DeleteLocalContactsUseCase @Inject constructor(val contactRepository: ContactRepository) :
    DeleteContactsUseCase {
    override suspend operator fun invoke(vararg contacts: ContactsItemUiState): Int {
        val contactEntities = contacts.map { contact: ContactsItemUiState ->
            ContactEntity(uri = contact.uri)
        }
        return contactRepository.delete(contacts = contactEntities.toTypedArray())
    }
}