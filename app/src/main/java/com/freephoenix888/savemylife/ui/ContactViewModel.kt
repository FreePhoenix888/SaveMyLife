package com.freephoenix888.savemylife.ui

import androidx.lifecycle.*
import com.freephoenix888.savemylife.data.db.entities.ContactEntity
import com.freephoenix888.savemylife.data.repositories.ContactRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ContactViewModel @Inject constructor (private val repository: ContactRepository): ViewModel() {

    val contacts = repository.getAll().asLiveData()

    suspend fun add(contact: ContactEntity) = withContext(Dispatchers.IO) {
        repository.insert(contact)
    }

    suspend fun remove(contact: ContactEntity) = withContext(Dispatchers.IO) {
        repository.remove(contact)
    }


}