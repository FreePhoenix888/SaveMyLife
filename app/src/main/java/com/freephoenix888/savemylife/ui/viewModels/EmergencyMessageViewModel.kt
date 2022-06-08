package com.freephoenix888.savemylife.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freephoenix888.savemylife.SecondsInterval
import com.freephoenix888.savemylife.domain.useCases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmergencyMessageViewModel @Inject constructor(
    val getEmergencyMessageUseCase: GetEmergencyMessageUseCase,
    val getEmergencyMessageTemplateFlowUseCase: GetEmergencyMessageTemplateFlowUseCase,
    val setEmergencyMessageTemplateUseCase: SetEmergencyMessageTemplateUseCase,
    val getEmergencyMessageSendingIntervalFlowUseCase: GetEmergencyMessageSendingIntervalUseCase,
    val setEmergencyMessageSendingIntervalUseCase: SetEmergencyMessageSendingIntervalUseCase,
    val getEmergencyContactsWithPhoneNumbersFlowUseCase: GetEmergencyContactsWithPhoneNumbersFlowUseCase
) : ViewModel() {

    init {
        viewModelScope.launch {
            val firstContactWithPhoneNumbers = getEmergencyContactsWithPhoneNumbersFlowUseCase().last().first()
            emergencyMessageExample = getEmergencyMessageUseCase(firstContactWithPhoneNumbers.contact)
        }
    }

    val messageTemplate = getEmergencyMessageTemplateFlowUseCase()
    fun setMessageTemplate(newMessageTemplate: String) = viewModelScope.launch {
        setEmergencyMessageTemplateUseCase(newMessageTemplate)
    }

    val sendingInterval = getEmergencyMessageSendingIntervalFlowUseCase()
    fun setSendingInterval(newSendingInterval: SecondsInterval) = viewModelScope.launch {
        setEmergencyMessageSendingIntervalUseCase(newSendingInterval)
    }

    lateinit var emergencyMessageExample: String
        private set
}
