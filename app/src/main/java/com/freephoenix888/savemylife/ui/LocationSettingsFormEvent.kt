package com.freephoenix888.savemylife.ui

sealed class LocationSettingsFormEvent {
    data class IsLocationSharingEnabledChanged(val isLocationSharingEnabled: Boolean) : LocationSettingsFormEvent()

    object Submit : LocationSettingsFormEvent()
}