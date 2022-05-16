package com.freephoenix888.savemylife.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.freephoenix888.savemylife.data.repositories.ContactRepository
import com.freephoenix888.savemylife.domain.useCases.DeleteContactsUseCase
import com.freephoenix888.savemylife.domain.useCases.IDeleteContactsUseCase
import com.freephoenix888.savemylife.domain.useCases.ISaveContactsUseCase
import com.freephoenix888.savemylife.domain.useCases.SaveContactsUseCase

class ContactViewModelFactory(
    private val repository: ContactRepository,
    private val saveContactsUseCase: ISaveContactsUseCase,
    private val deleteContactsUseCase: IDeleteContactsUseCase
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ContactViewModel(repository = repository, saveContactsUseCase = saveContactsUseCase, deleteContactsUseCase = deleteContactsUseCase) as T
    }
}