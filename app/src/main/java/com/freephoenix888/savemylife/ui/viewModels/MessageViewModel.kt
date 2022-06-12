package com.freephoenix888.savemylife.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freephoenix888.savemylife.SecondsInterval
import com.freephoenix888.savemylife.constants.PhoneNumberConstants
import com.freephoenix888.savemylife.domain.useCases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    val getMessageUseCase: GetMessageUseCase,
    val getMessageTemplateFlowUseCase: GetMessageTemplateFlowUseCase,
    val setMessageTemplateUseCase: SetMessageTemplateUseCase,
    val getMessageSendingIntervalFlowUseCase: GetMessageSendingIntervalUseCase,
    val setMessageSendingIntervalUseCase: SetMessageSendingIntervalUseCase,
    val getPhoneNumberListFlowUseCase: GetPhoneNumberListFlowUseCase
) : ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val phoneNumberList = getPhoneNumberListFlowUseCase().first()
            val firstContact =
                if (phoneNumberList.isEmpty()) PhoneNumberConstants.FAKE_PHONE_NUMBERS.first() else phoneNumberList.first()
            emergencyMessageExample =
                getMessageUseCase(firstContact)
        }
    }

    val messageTemplate = getMessageTemplateFlowUseCase()
    fun setMessageTemplate(newMessageTemplate: String) = viewModelScope.launch(Dispatchers.IO) {
        setMessageTemplateUseCase(newMessageTemplate)
    }

    val sendingInterval = getMessageSendingIntervalFlowUseCase()
    fun setSendingInterval(newSendingInterval: SecondsInterval) = viewModelScope.launch(Dispatchers.IO) {
        setMessageSendingIntervalUseCase(newSendingInterval)
    }

    var emergencyMessageExample: String? = null
        private set
}
