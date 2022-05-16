package com.freephoenix888.savemylife.ui

import android.util.Log
import androidx.lifecycle.*
import com.freephoenix888.savemylife.data.db.entities.ContactEntity
import com.freephoenix888.savemylife.data.repositories.ContactRepository
import kotlinx.coroutines.launch
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