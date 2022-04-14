package com.freephoenix888.savemylife.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.freephoenix888.savemylife.data.repositories.ContactRepository

class ContactViewModelFactory(
    private val repository: ContactRepository
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ContactViewModel(repository) as T
    }
}