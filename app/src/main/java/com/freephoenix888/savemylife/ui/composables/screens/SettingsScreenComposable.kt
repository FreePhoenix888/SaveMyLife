package com.freephoenix888.savemylife.ui.composables.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShareLocation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.freephoenix888.savemylife.R
import com.freephoenix888.savemylife.domain.models.SettingLink
import com.freephoenix888.savemylife.ui.SaveMyLifeScreenEnum
import com.freephoenix888.savemylife.ui.composables.SettingLinkComposable

@Composable
fun SettingsScreenComposable(
    navController: NavController
) {
    val settingLinkList = remember {
        listOf(
            SettingLink(
                icon = Icons.Filled.Message,
                title = "Phone numbers",
                onClick = {
                    navController.navigate(SaveMyLifeScreenEnum.PhoneNumber.name)
                }
            ),
            SettingLink(
                icon = Icons.Filled.Message,
                title = "Message",
                onClick = {
                    navController.navigate(SaveMyLifeScreenEnum.Message.name)
                }
            ),
            SettingLink(
                icon = Icons.Filled.ShareLocation,
                title = "Location",
                onClick = {
                    navController.navigate(SaveMyLifeScreenEnum.Location.name)
                }
            )
        )
    }
    SettingsScreenBodyComposable(settingsComposable = {
        settingLinkList.forEach { settingLink ->
            SettingLinkComposable(settingLink = settingLink)
        }
    })
}

@Composable
private fun SettingsScreenBodyComposable(settingsComposable: @Composable () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = stringResource(R.string.settings)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(stringResource(R.string.settings))
            }
        }
    ) { innerPadding: PaddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            settingsComposable()
        }
    }
}

@Preview
@Composable
private fun SettingsScreenBodyComposablePreview() {
    val settingLinkList = remember {
        listOf(
            SettingLink(
                icon = Icons.Filled.Message,
                title = "Phone numbers",
                onClick = {
                }
            ),
            SettingLink(
                icon = Icons.Filled.Message,
                title = "Message",
                onClick = {
                }
            ),
            SettingLink(
                icon = Icons.Filled.ShareLocation,
                title = "Location",
                onClick = {
                }
            )
        )
    }
    SettingsScreenBodyComposable(settingsComposable = {
        settingLinkList.forEach { settingLink ->
            SettingLinkComposable(settingLink = settingLink)
        }
    })
}