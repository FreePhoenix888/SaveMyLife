package com.freephoenix888.savemylife.ui.viewModels

import androidx.lifecycle.*
import com.freephoenix888.savemylife.data.storage.entities.ContactEntity
import com.freephoenix888.savemylife.data.repositories.ContactRepository
import com.freephoenix888.savemylife.domain.useCases.IDeleteContactsUseCase
import com.freephoenix888.savemylife.domain.useCases.ISaveContactsUseCase
import javax.inject.Inject

class ContactViewModel @Inject constructor (private val repository: ContactRepository, private val saveContactsUseCase: ISaveContactsUseCase, private val deleteContactsUseCase: IDeleteContactsUseCase): ViewModel() {

    val contacts = repository.allContacts.asLiveData()

    suspend fun insert(vararg contacts: ContactEntity): List<Long> {
        return saveContactsUseCase(contactRepository = repository, contacts = contacts)
    }

    suspend fun delete(vararg contacts: ContactEntity): Int {
        return deleteContactsUseCase(contactRepository = repository, contacts = contacts)
    }
}