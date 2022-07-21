package com.freephoenix888.savemylife.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freephoenix888.savemylife.domain.models.PhoneNumberSettingsFormState
import com.freephoenix888.savemylife.domain.useCases.DeletePhoneNumberUseCase
import com.freephoenix888.savemylife.domain.useCases.GetPhoneNumberListFlowUseCase
import com.freephoenix888.savemylife.domain.useCases.InsertPhoneNumbersUseCase
import com.freephoenix888.savemylife.ui.PhoneNumberSettingsFormEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhoneNumberSettingsViewModel @Inject constructor(
    private val insertPhoneNumbersUseCase: InsertPhoneNumbersUseCase,
    private val deletePhoneNumberUseCase: DeletePhoneNumberUseCase,
    getPhoneNumberListFlowUseCase: GetPhoneNumberListFlowUseCase
) : ViewModel() {

    init {
        viewModelScope.launch {
            getPhoneNumberListFlowUseCase().collect {
                _state.value = _state.value.copy(
                    phoneNumberList = it
                )
            }
        }
    }

    private val _state = MutableStateFlow(PhoneNumberSettingsFormState())
    val state: StateFlow<PhoneNumberSettingsFormState> = _state

    fun onEvent(event: PhoneNumberSettingsFormEvent) = viewModelScope.launch {
        when(event) {
            is PhoneNumberSettingsFormEvent.PhoneNumberAdded -> {
                insertPhoneNumbersUseCase(listOf(event.phoneNumber))
            }
            is PhoneNumberSettingsFormEvent.PhoneNumberDeleted -> {
                deletePhoneNumberUseCase(event.phoneNumber)
            }
        }

    }
}
