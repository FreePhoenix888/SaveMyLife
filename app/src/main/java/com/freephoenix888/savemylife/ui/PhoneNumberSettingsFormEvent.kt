package com.freephoenix888.savemylife.ui

import com.freephoenix888.savemylife.domain.models.PhoneNumber

sealed class PhoneNumberSettingsFormEvent {
    data class PhoneNumberAdded(val phoneNumber: PhoneNumber): PhoneNumberSettingsFormEvent()

    data class PhoneNumberDeleted(val phoneNumber: PhoneNumber): PhoneNumberSettingsFormEvent()

    object Submit : PhoneNumberSettingsFormEvent()
}