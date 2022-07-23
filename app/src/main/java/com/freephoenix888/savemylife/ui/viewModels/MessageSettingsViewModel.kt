package com.freephoenix888.savemylife.ui.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freephoenix888.savemylife.domain.models.ValidationResult
import com.freephoenix888.savemylife.domain.useCases.*
import com.freephoenix888.savemylife.mappers.MessageSettingsToMessageSettingsFormStateMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@HiltViewModel
class MessageSettingsViewModel @Inject constructor(
    private val getMessageSettingsFlowUseCase: GetMessageSettingsFlowUseCase,
    private val setMessageTemplateUseCase: SetMessageTemplateUseCase,
    private val validateMessageTemplateInputUseCase: ValidateMessageTemplateInputUseCase,
    private val setMessageSendingIntervalUseCase: SetMessageSendingIntervalUseCase,
    private val validateMessageSendingIntervalInputUseCase: ValidateMessageSendingIntervalInputUseCase,
    private val messageSettingsToMessageSettingsFormStateMapper: MessageSettingsToMessageSettingsFormStateMapper
) : ViewModel() {
    init {
        viewModelScope.launch(Dispatchers.IO) {
            getMessageSettingsFlowUseCase().collect {
                messageTemplate.value = it.template
                Log.d(null, "Sending interval: ${it.sendingInterval}")
                Log.d(null, "Sending interval in whole minutes: ${it.sendingInterval.inWholeMinutes}")
                sendingInterval.value = it.sendingInterval.inWholeMinutes.toString()
            }
        }
    }

    val messageTemplate = MutableStateFlow("")
    val messageTemplateErrorMessage = MutableStateFlow<String?>(null)
    fun onMessageTemplateChange(newMessageTemplate: String) {
        messageTemplate.value = newMessageTemplate
        when(val validationResult = validateMessageTemplateInputUseCase(newMessageTemplate)) {
            is ValidationResult.Error -> {
                messageTemplateErrorMessage.value = validationResult.message
            }
            is ValidationResult.Success -> {
                messageTemplateErrorMessage.value = null
            }
        }
    }
    fun submitMessageTemplate() = viewModelScope.launch {
        setMessageTemplateUseCase(messageTemplate.value)
    }

    val sendingInterval = MutableStateFlow("")
    val sendingIntervalErrorMessage = MutableStateFlow<String?>(null)
    fun onSendingIntervalChange(newSendingInterval: String) {
        sendingInterval.value = newSendingInterval
        when(val validationResult = validateMessageSendingIntervalInputUseCase(newSendingInterval)) {
            is ValidationResult.Error -> {
                sendingIntervalErrorMessage.value = validationResult.message
            }
            is ValidationResult.Success -> {
                sendingIntervalErrorMessage.value = null
            }
        }
    }
    fun submitSendingInterval() = viewModelScope.launch {
        Log.d(null, "submitSendingInterval: ${sendingInterval.value.toLong().toDuration(DurationUnit.MINUTES)}")
        Log.d(null, "submitSendingInterval: ${sendingInterval.value.toLong()}")
        setMessageSendingIntervalUseCase(sendingInterval.value.toLong().toDuration(DurationUnit.MINUTES))
    }
}
