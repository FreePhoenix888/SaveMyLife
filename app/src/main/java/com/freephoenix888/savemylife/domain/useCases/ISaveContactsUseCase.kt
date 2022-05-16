package com.freephoenix888.savemylife.domain.useCases

import com.freephoenix888.savemylife.data.repositories.ContactRepository
import com.freephoenix888.savemylife.data.storage.entities.ContactEntity

interface ISaveContactsUseCase {
    suspend operator fun invoke(contactRepository: ContactRepository, vararg contacts: ContactEntity) : List<Long>
}