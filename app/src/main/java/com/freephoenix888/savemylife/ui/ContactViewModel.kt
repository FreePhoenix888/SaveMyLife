package com.freephoenix888.savemylife.ui

import androidx.lifecycle.ViewModel
import com.freephoenix888.savemylife.data.db.entities.ContactEntity
import com.freephoenix888.savemylife.data.repositories.ContactRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactViewModel(private val repository: ContactRepository): ViewModel() {
    fun add(contact: ContactEntity) = CoroutineScope(Dispatchers.Main).launch {
        repository.insert(contact)
    }

    fun remove(contact: ContactEntity) = CoroutineScope(Dispatchers.Main).launch {
        repository.remove(contact)
    }

    fun getAll(contact: ContactEntity) = repository.getAll()

}