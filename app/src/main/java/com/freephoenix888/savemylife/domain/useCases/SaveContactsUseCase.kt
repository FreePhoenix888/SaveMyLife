package com.freephoenix888.savemylife.domain.useCases

import com.freephoenix888.savemylife.data.repositories.ContactRepository
import com.freephoenix888.savemylife.data.storage.entities.ContactEntity
import com.freephoenix888.savemylife.ui.viewModels.ContactViewModel

class SaveContactsUseCase : ISaveContactsUseCase {
    override suspend operator fun invoke(contactRepository: ContactRepository, vararg contacts: ContactEntity): List<Long>{
        return contactRepository.insert(contacts = contacts)
    }
}