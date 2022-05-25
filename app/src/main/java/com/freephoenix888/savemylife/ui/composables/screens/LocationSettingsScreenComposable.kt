package com.freephoenix888.savemylife.ui.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun LocationSettingsScreenComposable(
    isLocationSharingEnabled: Boolean,
    onIsLocationSharingEnabledChange: (Boolean) -> Unit
) {
    Row() {
        Text(text = "Location sharing")
        Switch(checked = isLocationSharingEnabled, onCheckedChange = onIsLocationSharingEnabledChange)
    }
}

@Preview
@Composable
private fun LocationSettingsScreenComposablePreview() {
    var locationSharingEnabled by remember { mutableStateOf(false) }
    LocationSettingsScreenComposable(isLocationSharingEnabled = locationSharingEnabled, onIsLocationSharingEnabledChange = { state: Boolean ->
        locationSharingEnabled = state
    })
}