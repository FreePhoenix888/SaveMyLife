package com.freephoenix888.savemylife.ui.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import androidx.lifecycle.*
import com.freephoenix888.savemylife.ui.states.ContactsItemUiState
import com.freephoenix888.savemylife.data.repositories.ContactRepository
import com.freephoenix888.savemylife.data.room.databases.entities.ContactEntity
import com.freephoenix888.savemylife.domain.useCases.interfaces.*
import com.freephoenix888.savemylife.ui.states.ContactsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val repository: ContactRepository,
    private val saveContactsUseCase: SaveContactsUseCase,
    private val deleteContactsUseCase: DeleteContactsUseCase,
    private val getContactByUriUseCase: GetContactByUriUseCase
) : ViewModel() {

    init {
        viewModelScope.launch {
            repository.contacts.collect() { contacts: List<ContactEntity> ->
                uiState = uiState.copy(
                    contacts = contacts.map { contactEntity: ContactEntity ->
                        getContactByUriUseCase(uri = contactEntity.uri.toUri())
                    }
                )
            }
        }
    }

    var uiState by mutableStateOf(ContactsUiState(contacts = listOf()))
        private set

    suspend fun insertContacts(vararg contacts: ContactsItemUiState): List<Long> {
        return saveContactsUseCase(contacts = contacts)
    }

    suspend fun deleteContacts(vararg contacts: ContactsItemUiState): Int {
        return deleteContactsUseCase(contacts = contacts)
    }
}