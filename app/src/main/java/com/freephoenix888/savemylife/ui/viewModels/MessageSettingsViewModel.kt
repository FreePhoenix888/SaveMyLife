package com.freephoenix888.savemylife.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freephoenix888.savemylife.domain.models.MessageSettingsFormState
import com.freephoenix888.savemylife.domain.useCases.*
import com.freephoenix888.savemylife.mappers.MessageSettingsToMessageSettingsFormStateMapper
import com.freephoenix888.savemylife.ui.MessageSettingsFormEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
    private val _state: MutableStateFlow<MessageSettingsFormState> = MutableStateFlow(MessageSettingsFormState())
    val state: StateFlow<MessageSettingsFormState> = _state

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getMessageSettingsFlowUseCase().collect {
                _state.value = messageSettingsToMessageSettingsFormStateMapper.map(it)
            }
        }
    }

    fun onEvent(event: MessageSettingsFormEvent) {
        when(event) {
            is MessageSettingsFormEvent.TemplateChanged -> {
                _state.value = _state.value.copy(template = event.template, templateErrorMessage = null)
            }
            is MessageSettingsFormEvent.SendingIntervalInMinutesChanged -> {
                _state.value = _state.value.copy(sendingIntervalInMinutes = event.sendingIntervalInMinutes, sendingIntervalInMinutesErrorMessage = null)
            }
            is MessageSettingsFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val templateValidationResult = validateMessageTemplateInputUseCase(state.value.template)
        val sendingIntervalValidationResult = validateMessageSendingIntervalInputUseCase(state.value.sendingIntervalInMinutes)
        val validationResults = listOf(
            templateValidationResult,
            sendingIntervalValidationResult
        )
        val hasError = validationResults.any { !it.isValid }
        if(hasError) {
            _state.value = _state.value.copy(
                templateErrorMessage = templateValidationResult.errorMessage,
                sendingIntervalInMinutesErrorMessage = sendingIntervalValidationResult.errorMessage
            )
            return
        }

        viewModelScope.launch {
            setMessageTemplateUseCase(state.value.template)
            setMessageSendingIntervalUseCase(state.value.sendingIntervalInMinutes.toLong().toDuration(DurationUnit.MINUTES))
        }
    }
}
