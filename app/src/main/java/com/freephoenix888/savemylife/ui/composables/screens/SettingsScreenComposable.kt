package com.freephoenix888.savemylife.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.ShareLocation
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.freephoenix888.savemylife.data.models.Setting
import com.freephoenix888.savemylife.ui.SaveMyLifeScreen

val settingsList = listOf(
    Setting(
        iconVector = Icons.Filled.Contacts,
        name = "Contacts",
        screenName = SaveMyLifeScreen.ContactsSettings.name
    ),
    Setting(
        iconVector = Icons.Filled.Message,
        name = "Messages",
        screenName = SaveMyLifeScreen.MessagesSettings.name
    ),
    Setting(
        iconVector = Icons.Filled.ShareLocation,
        name = "Location",
        screenName = SaveMyLifeScreen.LocationSettings.name
    )
)

@Composable
fun SettingsScreenComposable(
    onSettingClick: (setting: Setting) -> Unit
) {
    Column {
        settingsList.forEach { setting ->
            SettingComposable(setting = setting, onSettingClick = {
                onSettingClick(setting)
            })
        }
    }
}

@Preview
@Composable
private fun SettingsScreenComposablePreview() {
    val context = LocalContext.current
    SettingsScreenComposable(onSettingClick = {
        DefaultValuesForPreviews.defaultOnSettingClick(context)
    })
}