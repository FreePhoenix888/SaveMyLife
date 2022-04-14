package com.freephoenix888.savemylife.ui

import androidx.lifecycle.ViewModel
import com.freephoenix888.savemylife.data.db.entities.Contact
import com.freephoenix888.savemylife.data.repositories.ContactRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactViewModel(private val repository: ContactRepository): ViewModel() {
    fun add(contact: Contact) = CoroutineScope(Dispatchers.Main).launch {
        repository.add(contact)
    }

    fun remove(contact: Contact) = CoroutineScope(Dispatchers.Main).launch {
        repository.remove(contact)
    }

    fun getAll(contact: Contact) = repository.getAll()

}