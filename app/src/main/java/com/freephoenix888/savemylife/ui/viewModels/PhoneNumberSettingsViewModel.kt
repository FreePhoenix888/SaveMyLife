package com.freephoenix888.savemylife.ui.viewModels

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freephoenix888.savemylife.domain.models.PhoneNumber
import com.freephoenix888.savemylife.domain.useCases.DeletePhoneNumberUseCase
import com.freephoenix888.savemylife.domain.useCases.GetPhoneNumberByContentUriUseCase
import com.freephoenix888.savemylife.domain.useCases.GetPhoneNumberListFlowUseCase
import com.freephoenix888.savemylife.domain.useCases.InsertPhoneNumbersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhoneNumberSettingsViewModel @Inject constructor(
    private val insertPhoneNumbersUseCase: InsertPhoneNumbersUseCase,
    private val deletePhoneNumberUseCase: DeletePhoneNumberUseCase,
    getPhoneNumberListFlowUseCase: GetPhoneNumberListFlowUseCase,
    private val getPhoneNumberByContentUriUseCase: GetPhoneNumberByContentUriUseCase
) : ViewModel() {

    init {
        viewModelScope.launch {
            getPhoneNumberListFlowUseCase().collect {
                _phoneNumbers.value = it
            }
        }
    }

    private val _phoneNumbers = MutableStateFlow<List<PhoneNumber>>(listOf())
    val phoneNumbers: StateFlow<List<PhoneNumber>> = _phoneNumbers

    fun addPhoneNumber(phoneNumber: PhoneNumber) = viewModelScope.launch {
        insertPhoneNumbersUseCase(listOf(phoneNumber))
    }

    fun removePhoneNumber(phoneNumber: PhoneNumber) = viewModelScope.launch {
        deletePhoneNumberUseCase(phoneNumber)
    }

    fun getPhoneNumberByContentUri(context: Context, contentUri: Uri): PhoneNumber {
        return getPhoneNumberByContentUriUseCase(context, contentUri)
    }
}
