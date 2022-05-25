package com.freephoenix888.savemylife.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.freephoenix888.savemylife.data.repositories.ContactRepository
import com.freephoenix888.savemylife.domain.useCases.interfaces.DeleteContactsUseCase
import com.freephoenix888.savemylife.domain.useCases.interfaces.GetContactByUriUseCase
import com.freephoenix888.savemylife.domain.useCases.interfaces.SaveContactsUseCase

class ContactsViewModelFactory(
    private val repository: ContactRepository,
    private val saveContactsUseCase: SaveContactsUseCase,
    private val deleteContactsUseCase: DeleteContactsUseCase,
    private val getContactByUriUseCase: GetContactByUriUseCase
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ContactsViewModel(
            repository = repository,
            saveContactsUseCase = saveContactsUseCase,
            deleteContactsUseCase = deleteContactsUseCase,
            getContactByUriUseCase = getContactByUriUseCase
        ) as T
    }
}