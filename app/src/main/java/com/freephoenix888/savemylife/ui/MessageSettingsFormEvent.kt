package com.freephoenix888.savemylife.ui

sealed class MessageSettingsFormEvent {
    data class TemplateChanged(val template: String) : MessageSettingsFormEvent()
    data class sendingIntervalInSecondsChanged(val sendingIntervalInSeconds: String) : MessageSettingsFormEvent()

    object Submit: MessageSettingsFormEvent()
}