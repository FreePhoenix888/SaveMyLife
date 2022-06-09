package com.freephoenix888.savemylife.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freephoenix888.savemylife.domain.models.ContactPhoneNumber
import com.freephoenix888.savemylife.domain.useCases.DeleteEmergencyContactPhoneNumberUseCase
import com.freephoenix888.savemylife.domain.useCases.DeleteEmergencyContactPhoneNumbersUseCase
import com.freephoenix888.savemylife.domain.useCases.InsertEmergencyContactPhoneNumberUseCase
import com.freephoenix888.savemylife.domain.useCases.InsertEmergencyContactPhoneNumbersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmergencyContactPhoneNumberViewModel @Inject constructor(
    private val insertEmergencyContactPhoneNumberUseCase: InsertEmergencyContactPhoneNumberUseCase,
    private val insertEmergencyContactPhoneNumbersUseCase: InsertEmergencyContactPhoneNumbersUseCase,
    private val deleteEmergencyContactPhoneNumberUseCase: DeleteEmergencyContactPhoneNumberUseCase,
    private val deleteEmergencyContactPhoneNumbersUseCase: DeleteEmergencyContactPhoneNumbersUseCase
) : ViewModel() {
    fun insert(contactPhoneNumber: ContactPhoneNumber) = viewModelScope.launch {
        insertEmergencyContactPhoneNumberUseCase(contactPhoneNumber)
    }

    fun insertList(contactPhoneNumberList: List<ContactPhoneNumber>) = viewModelScope.launch {
        insertEmergencyContactPhoneNumbersUseCase(contactPhoneNumberList)
    }

    fun delete(contactPhoneNumber: ContactPhoneNumber) = viewModelScope.launch {
        deleteEmergencyContactPhoneNumberUseCase(contactPhoneNumber)
    }

    fun deleteList(contactPhoneNumberList: List<ContactPhoneNumber>) = viewModelScope.launch {
        deleteEmergencyContactPhoneNumbersUseCase(contactPhoneNumberList)
    }
}
