package com.freephoenix888.savemylife.ui.viewModels

import androidx.lifecycle.*
import com.freephoenix888.savemylife.data.storage.entities.ContactEntity
import com.freephoenix888.savemylife.data.repositories.ContactRepository
import com.freephoenix888.savemylife.domain.useCases.DeleteContactsUseCase
import com.freephoenix888.savemylife.domain.useCases.IDeleteContactsUseCase
import com.freephoenix888.savemylife.domain.useCases.ISaveContactsUseCase
import com.freephoenix888.savemylife.domain.useCases.SaveContactsUseCase
import javax.inject.Inject

class ContactViewModel @Inject constructor (private val _repository: ContactRepository, private val _saveContactsUseCase: ISaveContactsUseCase, private val _deleteContactsUseCase: IDeleteContactsUseCase): ViewModel() {

    val contacts = _repository.allContacts.asLiveData()

    suspend fun insert(vararg contacts: ContactEntity): List<Long> {
        return _saveContactsUseCase(contactRepository = _repository, contacts = contacts)
    }

    suspend fun delete(vararg contacts: ContactEntity): Int {
        return _deleteContactsUseCase(contactRepository = _repository, contacts = contacts)
    }
}