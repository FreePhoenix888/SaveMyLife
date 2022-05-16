package com.freephoenix888.savemylife.ui.viewModels

import androidx.lifecycle.*
import com.freephoenix888.savemylife.data.storage.entities.ContactEntity
import com.freephoenix888.savemylife.data.repositories.ContactRepository
import javax.inject.Inject

class ContactViewModel @Inject constructor (private val repository: ContactRepository): ViewModel() {

    val contacts = repository.allContacts.asLiveData()

    suspend fun insert(vararg contacts: ContactEntity): List<Long> {
        return repository.insert(*contacts)
    }

    suspend fun delete(vararg contacts: ContactEntity): Int {
        return repository.delete(contacts = contacts)
    }
}