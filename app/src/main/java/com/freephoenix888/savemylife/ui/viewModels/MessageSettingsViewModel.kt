package com.freephoenix888.savemylife.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freephoenix888.savemylife.constants.MessageTemplateVariables
import com.freephoenix888.savemylife.domain.models.ValidationResult
import com.freephoenix888.savemylife.domain.useCases.*
import com.freephoenix888.savemylife.mappers.MessageSettingsToMessageSettingsFormStateMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
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
    private val getIsMessageCommandsEnabledFlowUseCase: GetIsMessageCommandsEnabledFlowUseCase,
    private val setIsMessageCommandsEnabledUseCase: SetIsMessageCommandsEnabledUseCase,
    private val messageSettingsToMessageSettingsFormStateMapper: MessageSettingsToMessageSettingsFormStateMapper,
    private val getIsLocationSharingEnabledFlowUseCase: GetIsLocationSharingEnabledFlowUseCase,
) : ViewModel() {
    fun getIsSaveableFlow(flow1: Flow<String?>, flow2: Flow<Boolean>) = combine(flow = flow1, flow2 = flow2, transform = { errorMessage, hasNotSavedChanged ->
            (errorMessage == null) && hasNotSavedChanged
        })

    val messageTemplate = MutableStateFlow("")
    val messageTemplateErrorMessage = MutableStateFlow<String?>(null)
    val isMessageTemplateHasNotSavedChanged = MutableStateFlow(false)
    val isMessageTemplateSaveable: Flow<Boolean> = getIsSaveableFlow(flow1 = messageTemplateErrorMessage, flow2 = isMessageTemplateHasNotSavedChanged)
        fun onMessageTemplateChange(newMessageTemplate: String) = viewModelScope.launch{
        messageTemplate.value = newMessageTemplate
        isMessageTemplateHasNotSavedChanged.value = true
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
        isMessageTemplateHasNotSavedChanged.value = false
    }

    val sendingInterval = MutableStateFlow("")
    val sendingIntervalErrorMessage = MutableStateFlow<String?>(null)
    val isSendingIntervalHasNotSavedChanges = MutableStateFlow(false)
    val isSendingIntervalSaveable: Flow<Boolean> = getIsSaveableFlow(flow1 = sendingIntervalErrorMessage, flow2 = isSendingIntervalHasNotSavedChanges)

    fun onSendingIntervalChange(newSendingInterval: String) {
        sendingInterval.value = newSendingInterval
        isSendingIntervalHasNotSavedChanges.value = true
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
        setMessageSendingIntervalUseCase(sendingInterval.value.toLong().toDuration(DurationUnit.MINUTES))
        isSendingIntervalHasNotSavedChanges.value = false
    }

    var isMessageCommandsEnabled = MutableStateFlow(false)
    fun setIsMessageCommandsEnabled(newIsMessageCommandsEnabled: Boolean) = viewModelScope.launch {
        setIsMessageCommandsEnabledUseCase(newIsMessageCommandsEnabled)
    }

    val isLocationSharingEnabled = MutableStateFlow(false)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getMessageSettingsFlowUseCase().collect {
                onMessageTemplateChange(it.template)
                onSendingIntervalChange(it.sendingInterval.inWholeMinutes.toString())
                isMessageCommandsEnabled.value = it.isMessageCommandsEnabled
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            getIsLocationSharingEnabledFlowUseCase().collect() {
                isLocationSharingEnabled.value = it
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            getIsMessageCommandsEnabledFlowUseCase()
        }
    }

    fun isMessageTemplateVariableAvailable(messageTemplateVariable: MessageTemplateVariables): Boolean {
        return when(messageTemplateVariable) {
            MessageTemplateVariables.LOCATION_URL -> isLocationSharingEnabled.value
            MessageTemplateVariables.MESSAGE_COMMANDS -> isMessageCommandsEnabled.value
            else -> true
        }

    }


}
