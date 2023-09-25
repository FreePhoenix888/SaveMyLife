package com.freephoenix888.savemylife.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freephoenix888.savemylife.domain.models.ValidationResult
import com.freephoenix888.savemylife.domain.useCases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@HiltViewModel
class MessageSettingsViewModel @Inject constructor(
    private val getMessageSettingsFlowUseCase: GetMessageSettingsFlowUseCase,
    private val setMessageTemplateUseCase: SetMessageTemplateUseCase,
    private val validateMessageTemplateInputUseCase: ValidateMessageTemplateInputUseCase,
    private val setMessageSendingIntervalUseCase: SetMessageSendingIntervalUseCase,
    private val validateMessageSendingIntervalInputUseCase: ValidateMessageSendingIntervalInputUseCase,
    private val getIsMessageCommandsEnabledFlowUseCase: GetIsMessageCommandsEnabledFlowUseCase,
    private val setIsMessageCommandsEnabledUseCase: SetIsMessageCommandsEnabledUseCase,
    private val getIsLocationSharingEnabledFlowUseCase: GetIsLocationSharingEnabledFlowUseCase
) : ViewModel() {

    private fun <T> getIsSaveableFlow(flow1: Flow<T?>, flow2: Flow<Boolean>): Flow<Boolean> =
        combine(flow1, flow2) { item1, item2 -> item1 == null && item2 }

    val messageTemplate = MutableStateFlow("")
    val messageTemplateError = MutableStateFlow<String?>(null)
    val hasMessageTemplateChanged = MutableStateFlow(false)
    val isMessageTemplateSaveable = getIsSaveableFlow(messageTemplateError, hasMessageTemplateChanged)

    fun onMessageTemplateChange(newMessageTemplate: String) = viewModelScope.launch {
        messageTemplate.value = newMessageTemplate
        hasMessageTemplateChanged.value = true
        validateMessageTemplate(newMessageTemplate)
    }

    private fun validateMessageTemplate(template: String) = viewModelScope.launch {
        validateMessageTemplateInputUseCase(template).let {
            messageTemplateError.value = when(it) {
                is ValidationResult.Error -> it.message
                is ValidationResult.Success -> null
            }
        }
    }

    fun submitMessageTemplate() = viewModelScope.launch {
        setMessageTemplateUseCase(messageTemplate.value)
        hasMessageTemplateChanged.value = false
    }

    val sendingInterval = MutableStateFlow(Duration.ZERO)
    val sendingIntervalUiState = MutableStateFlow("")
    val sendingIntervalError = MutableStateFlow<String?>(null)
    val hasSendingIntervalChanged = MutableStateFlow(false)
    val isSendingIntervalSaveable = getIsSaveableFlow(sendingIntervalError, hasSendingIntervalChanged)

    fun onSendingIntervalChange(newSendingInterval: String) = viewModelScope.launch {
        sendingIntervalUiState.value = newSendingInterval
        hasSendingIntervalChanged.value = true
        validateSendingInterval(newSendingInterval)
    }

    private fun validateSendingInterval(interval: String) = viewModelScope.launch {
        validateMessageSendingIntervalInputUseCase(interval).let {
            sendingIntervalError.value = when(it) {
                is ValidationResult.Error -> it.message
                is ValidationResult.Success -> null
            }
        }
    }

    fun submitSendingInterval() = viewModelScope.launch {
        setMessageSendingIntervalUseCase(
            sendingIntervalUiState.value.toLong().toDuration(DurationUnit.MINUTES)
        )
        hasSendingIntervalChanged.value = false
    }

    val isMessageCommandsEnabled = MutableStateFlow(false)

    fun setIsMessageCommandsEnabled(newIsMessageCommandsEnabled: Boolean) = viewModelScope.launch {
        setIsMessageCommandsEnabledUseCase(newIsMessageCommandsEnabled)
    }

    private val isLocationSharingEnabled = MutableStateFlow(false)

    init {
        fetchData()
    }

    private fun fetchData() = viewModelScope.launch {
        getMessageSettingsFlowUseCase().collect {
            messageTemplate.value = it.template
            validateMessageTemplate(messageTemplate.value)

            sendingInterval.value = it.sendingInterval
            sendingIntervalUiState.value = sendingInterval.value.inWholeMinutes.toString()
            validateSendingInterval(sendingIntervalUiState.value)

            isMessageCommandsEnabled.value = it.isMessageCommandsEnabled
        }

        getIsLocationSharingEnabledFlowUseCase().collect {
            isLocationSharingEnabled.value = it
        }

        getIsMessageCommandsEnabledFlowUseCase().collect {
            isMessageCommandsEnabled.value = it
        }
    }
}
