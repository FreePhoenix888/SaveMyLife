package com.freephoenix888.savemylife.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freephoenix888.savemylife.domain.models.PhoneNumber
import com.freephoenix888.savemylife.domain.useCases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhoneNumberViewModel @Inject constructor(
    private val insertPhoneNumberUseCase: InsertPhoneNumberUseCase,
    private val insertPhoneNumbersUseCase: InsertPhoneNumbersUseCase,
    private val deletePhoneNumberUseCase: DeletePhoneNumberUseCase,
    private val deletePhoneNumbersUseCase: DeletePhoneNumbersUseCase,
    private val getPhoneNumberListFlowUseCase: GetPhoneNumberListFlowUseCase
) : ViewModel() {

    val contactPhoneNumberList = getPhoneNumberListFlowUseCase()

    fun insert(contactPhoneNumber: PhoneNumber) = viewModelScope.launch(Dispatchers.IO) {
        insertPhoneNumberUseCase(contactPhoneNumber)
    }

    fun insertList(contactPhoneNumberList: List<PhoneNumber>) = viewModelScope.launch(Dispatchers.IO) {
        insertPhoneNumbersUseCase(contactPhoneNumberList)
    }

    fun delete(contactPhoneNumber: PhoneNumber) = viewModelScope.launch(Dispatchers.IO) {
        deletePhoneNumberUseCase(contactPhoneNumber)
    }

    fun deleteList(contactPhoneNumberList: List<PhoneNumber>) = viewModelScope.launch(Dispatchers.IO) {
        deletePhoneNumbersUseCase(contactPhoneNumberList)
    }
}
