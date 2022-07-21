package com.freephoenix888.savemylife.ui

sealed class MessageSettingsFormEvent {
    data class TemplateChanged(val template: String) : MessageSettingsFormEvent()
    data class SendingIntervalInMinutesChanged(val sendingIntervalInMinutes: String) : MessageSettingsFormEvent()

    object Submit: MessageSettingsFormEvent()
}