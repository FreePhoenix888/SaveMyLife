package com.freephoenix888.savemylife.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freephoenix888.savemylife.domain.models.Contact
import com.freephoenix888.savemylife.domain.models.ContactWithPhoneNumbers
import com.freephoenix888.savemylife.domain.useCases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmergencyContactViewModel @Inject constructor(
    private val insertEmergencyContactUseCase: InsertEmergencyContactUseCase,
    private val insertEmergencyContactsUseCase: InsertEmergencyContactsUseCase,
    private val deleteEmergencyContactUseCase: DeleteEmergencyContactUseCase,
    private val deleteEmergencyContactsUseCase: DeleteEmergencyContactsUseCase,
    private val getEmergencyContactsWithPhoneNumbersFlowUseCase: GetEmergencyContactsWithPhoneNumbersFlowUseCase
) : ViewModel() {

    var emergencyContactsWithPhoneNumbers: Flow<List<ContactWithPhoneNumbers>> = getEmergencyContactsWithPhoneNumbersFlowUseCase()
        private set

    fun insert(contact: Contact) {
        viewModelScope.launch {
            insertEmergencyContactUseCase(contact = contact)
        }
    }


    fun insertList(contacts: List<Contact>) {
        viewModelScope.launch {
            insertEmergencyContactsUseCase(contacts = contacts)
        }
    }

    fun delete(contact: Contact) {
        viewModelScope.launch {
            deleteEmergencyContactUseCase(contact = contact)
        }
    }

    fun deleteList(contacts: List<Contact>) {
        viewModelScope.launch {
            deleteEmergencyContactsUseCase(contacts = contacts)
        }
    }
}