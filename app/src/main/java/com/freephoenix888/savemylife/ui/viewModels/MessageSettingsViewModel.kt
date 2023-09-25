package com.freephoenix888.savemylife.ui.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freephoenix888.savemylife.constants.MessageConstants
import com.freephoenix888.savemylife.domain.models.ValidationResult
import com.freephoenix888.savemylife.domain.useCases.GetIsLocationSharingEnabledFlowUseCase
import com.freephoenix888.savemylife.domain.useCases.GetIsMessageCommandsEnabledFlowUseCase
import com.freephoenix888.savemylife.domain.useCases.GetMessageSettingsFlowUseCase
import com.freephoenix888.savemylife.domain.useCases.SetIsMessageCommandsEnabledUseCase
import com.freephoenix888.savemylife.domain.useCases.SetMessageSendingIntervalUseCase
import com.freephoenix888.savemylife.domain.useCases.SetMessageTemplateUseCase
import com.freephoenix888.savemylife.domain.useCases.ValidateMessageSendingIntervalInputUseCase
import com.freephoenix888.savemylife.domain.useCases.ValidateMessageTemplateInputUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import timber.log.Timber
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

    private val TAG = this::class.simpleName

    // Message Template related members
    val messageTemplate = MutableStateFlow(MessageConstants.DEFAULT_TEMPLATE)
    val messageTemplateError = MutableStateFlow<String?>(null)
    val hasMessageTemplateChanged = MutableStateFlow(false)
    val isMessageTemplateSaveable = isSaveableFlow(messageTemplateError, hasMessageTemplateChanged)

    // Sending Interval related members
    val sendingInterval = MutableStateFlow(Duration.ZERO)
    val sendingIntervalUiState = MutableStateFlow("")
    val sendingIntervalError = MutableStateFlow<String?>(null)
    val hasSendingIntervalChanged = MutableStateFlow(false)
    val isSendingIntervalSaveable = isSaveableFlow(sendingIntervalError, hasSendingIntervalChanged)

    // Message Commands related members
    val isMessageCommandsEnabled = MutableStateFlow(false)
    private val isLocationSharingEnabled = MutableStateFlow(false)

    init {
        fetchData()
    }

    fun onMessageTemplateChange(newMessageTemplate: String) = viewModelScope.launch {
        Timber.d( "onMessageTemplateChange: newMessageTemplate: $newMessageTemplate")
        messageTemplate.value = newMessageTemplate
        hasMessageTemplateChanged.value = true
        validateMessageTemplate(newMessageTemplate)
    }

    fun onSendingIntervalChange(newSendingInterval: String) = viewModelScope.launch {
        sendingIntervalUiState.value = newSendingInterval
        hasSendingIntervalChanged.value = true
        validateSendingInterval(newSendingInterval)
    }

    fun setIsMessageCommandsEnabled(newIsMessageCommandsEnabled: Boolean) = viewModelScope.launch {
        setIsMessageCommandsEnabledUseCase(newIsMessageCommandsEnabled)
    }

    fun submitMessageTemplate() = viewModelScope.launch {
        setMessageTemplateUseCase(messageTemplate.value)
        hasMessageTemplateChanged.value = false
    }

    fun submitSendingInterval() = viewModelScope.launch {
        setMessageSendingIntervalUseCase(
            sendingIntervalUiState.value.toLong().toDuration(DurationUnit.MINUTES)
        )
        hasSendingIntervalChanged.value = false
    }

    private fun <T> isSaveableFlow(flow1: Flow<T?>, flow2: Flow<Boolean>): Flow<Boolean> =
        combine(flow1, flow2) { item1, item2 -> item1 == null && item2 }

    private fun validateMessageTemplate(template: String) {
        Timber.d( "validateMessageTemplate: template: $template")
        validate(template, sendingIntervalError) { input -> validateMessageTemplateInputUseCase(input) }
    }


    private fun validateSendingInterval(interval: String) {
        validate(interval, sendingIntervalError) { input -> validateMessageSendingIntervalInputUseCase(input) }
    }

    private fun validate(input: String, errorFlow: MutableStateFlow<String?>, useCase: suspend (String) -> ValidationResult) {
        viewModelScope.launch {
            useCase(input).applyToFlow(errorFlow)
        }
    }

    private fun ValidationResult.applyToFlow(flow: MutableStateFlow<String?>) {
        flow.value = when (this) {
            is ValidationResult.Error -> message
            is ValidationResult.Success -> null
        }
    }

    private fun fetchData() = viewModelScope.launch {
        getMessageSettingsFlowUseCase().collect { settings ->
            Timber.d( "fetchData: settings: $settings")

            Timber.d( "fetchData: settings.template: ${settings.template}")
            messageTemplate.value = settings.template
            validateMessageTemplate(messageTemplate.value)

            sendingInterval.value = settings.sendingInterval
            sendingIntervalUiState.value = sendingInterval.value.inWholeMinutes.toString()
            validateSendingInterval(sendingIntervalUiState.value)

            isMessageCommandsEnabled.value = settings.isMessageCommandsEnabled
        }

        getIsLocationSharingEnabledFlowUseCase().collect {
            isLocationSharingEnabled.value = it
        }

        getIsMessageCommandsEnabledFlowUseCase().collect {
            isMessageCommandsEnabled.value = it
        }
    }

}
