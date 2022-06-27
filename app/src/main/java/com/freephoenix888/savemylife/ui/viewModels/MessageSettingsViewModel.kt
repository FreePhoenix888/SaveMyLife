package com.freephoenix888.savemylife.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freephoenix888.savemylife.domain.models.MessageSettingsFormState
import com.freephoenix888.savemylife.domain.useCases.*
import com.freephoenix888.savemylife.mappers.MessageSettingsToMessageSettingsFormStateMapper
import com.freephoenix888.savemylife.ui.MessageSettingsFormEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@HiltViewModel
class MessageSettingsViewModel @Inject constructor(
    val getMessageSettingsFlowUseCase: GetMessageSettingsFlowUseCase,
    val setMessageTemplateUseCase: SetMessageTemplateUseCase,
    val validateMessageTemplateInputUseCase: ValidateMessageTemplateInputUseCase,
    val setMessageSendingIntervalUseCase: SetMessageSendingIntervalUseCase,
    val validateMessageSendingIntervalInputUseCase: ValidateMessageSendingIntervalInputUseCase,
    val messageSettingsToMessageSettingsFormStateMapper: MessageSettingsToMessageSettingsFormStateMapper
) : ViewModel() {

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()
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
        if(event !is MessageSettingsFormEvent.Submit) {
            makeErrorMessagesNull()
        }
        when(event) {
            is MessageSettingsFormEvent.TemplateChanged -> {
                _state.value = _state.value.copy(template = event.template)
            }
            is MessageSettingsFormEvent.sendingIntervalInSecondsChanged -> {
                _state.value = _state.value.copy(sendingIntervalInSeconds = event.sendingIntervalInSeconds)
            }
            is MessageSettingsFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun makeErrorMessagesNull() {
        _state.value = _state.value.copy(
            templateErrorMessage = null,
            sendingIntervalInSecondsErrorMessage = null
        )
    }

    private fun submitData() {
        val templateValidationResult = validateMessageTemplateInputUseCase(state.value.template)
        val sendingIntervalValidationResult = validateMessageSendingIntervalInputUseCase(state.value.sendingIntervalInSeconds)
        val validationResults = listOf(
            templateValidationResult,
            sendingIntervalValidationResult
        )
        val hasError = validationResults.any { !it.isValid }
        if(hasError) {
            _state.value = _state.value.copy(
                templateErrorMessage = templateValidationResult.errorMessage,
                sendingIntervalInSecondsErrorMessage = sendingIntervalValidationResult.errorMessage
            )
            return
        }

        viewModelScope.launch {
            setMessageTemplateUseCase(state.value.template)
            setMessageSendingIntervalUseCase(state.value.sendingIntervalInSeconds.toLong().toDuration(DurationUnit.SECONDS))
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    sealed class ValidationEvent{
        object Success: ValidationEvent()
    }

}
