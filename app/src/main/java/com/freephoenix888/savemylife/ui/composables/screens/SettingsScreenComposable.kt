package com.freephoenix888.savemylife.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.ShareLocation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.alorma.compose.settings.ui.SettingsMenuLink
import com.freephoenix888.savemylife.domain.models.Setting
import com.freephoenix888.savemylife.ui.SaveMyLifeScreenEnum

val settingsList = listOf(
    Setting(
        iconVector = Icons.Filled.Contacts,
        title = "Contacts",
        screen = SaveMyLifeScreenEnum.ContactsSettings
    ),
    Setting(
        iconVector = Icons.Filled.Message,
        title = "Messages",
        screen = SaveMyLifeScreenEnum.MessagesSettings
    ),
    Setting(
        iconVector = Icons.Filled.ShareLocation,
        title = "Location",
        screen = SaveMyLifeScreenEnum.LocationSettings
    )
)

@Composable
fun SettingsScreenComposable(
    navController: NavController
) {
    SettingsScreenBodyComposable(settingOnClick = {setting ->
        navController.navigate(setting.screen.name)
    })
}

@Composable
private fun SettingsScreenBodyComposable(settingOnClick: (Setting) -> Unit) {
    Scaffold { innerPadding: PaddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            settingsList.forEach { setting ->
                SettingsMenuLink(title = {
                    Text(setting.title)
                },
                    icon = {
                        Icon(imageVector = setting.iconVector, contentDescription = setting.title)
                    },
                    onClick = {
                        settingOnClick(setting)
                    }
                )
            }
        }

    }

}

@Preview
@Composable
private fun SettingsScreenBodyComposablePreview() {
    SettingsScreenBodyComposable(settingOnClick = {setting ->

    })
}