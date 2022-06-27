package com.freephoenix888.savemylife.ui.composables.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Phone
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
    val settingLinkList = listOf(
        SettingLink(
            icon = Icons.Filled.Phone,
            title = stringResource(R.string.all_phone_numbers),
            onClick = {
                navController.navigate(SaveMyLifeScreenEnum.SmsSettings.name)
            }
        ),
        SettingLink(
            icon = Icons.Filled.Message,
            title = stringResource(R.string.all_message),
            onClick = {
                navController.navigate(SaveMyLifeScreenEnum.MessageSettings.name)
            }
        ),
        SettingLink(
            icon = Icons.Filled.ShareLocation,
            title = stringResource(R.string.all_location),
            onClick = {
                navController.navigate(SaveMyLifeScreenEnum.LocationSettings.name)
            }
        )
    )
    SettingsScreenBodyComposable(settingLinkList)
}

@Composable
private fun SettingsScreenBodyComposable(settingLinkList: List<SettingLink>) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = stringResource(R.string.all_settings)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(stringResource(R.string.all_settings))
            })
        }
    ) { innerPadding: PaddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            settingLinkList.forEach { settingLink ->
                SettingLinkComposable(settingLink = settingLink)
            }
        }
    }
}

@Preview
@Composable
private fun SettingsScreenBodyComposablePreview() {
    val settingLinkList = remember {
        List(3) {
            SettingLink(
                icon = Icons.Filled.Settings,
                title = "Setting",
                onClick = {
                }
            )
        }
    }
    SettingsScreenBodyComposable(settingLinkList)
}