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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.freephoenix888.savemylife.R
import com.freephoenix888.savemylife.ui.SaveMyLifeScreenEnum
import com.freephoenix888.savemylife.ui.composables.settings.SettingLink
import com.freephoenix888.savemylife.ui.composables.settings.SettingSwitch
import com.freephoenix888.savemylife.ui.viewModels.LocationSharingSettingsViewModel

@Composable
fun SettingsScreen(
    navController: NavController,
    locationSharingSettingsViewModel: LocationSharingSettingsViewModel
) {
//    val settingLinkList = listOf(
//        SettingLink(
//            icon = Icons.Filled.Phone,
//            title = stringResource(R.string.all_phone_numbers),
//            onClick = {
//                navController.navigate(SaveMyLifeScreenEnum.SmsSettings.name)
//            }
//        ),
//        SettingLink(
//            icon = Icons.Filled.Message,
//            title = stringResource(R.string.all_message),
//            onClick = {
//                navController.navigate(SaveMyLifeScreenEnum.MessageSettings.name)
//            }
//        ),
//        SettingLink(
//            icon = Icons.Filled.ShareLocation,
//            title = stringResource(R.string.all_location),
//            onClick = {
//                navController.navigate(SaveMyLifeScreenEnum.LocationSettings.name)
//            }
//        )
//    )

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
            SettingLink(icon = {
                Icon(
                    imageVector = Icons.Filled.Phone,
                    contentDescription = stringResource(R.string.all_message)
                )
            }, title = {
                Text(stringResource(R.string.all_phone_numbers))
            }, onClick = {
                navController.navigate(SaveMyLifeScreenEnum.SmsSettings.name)
            })
            SettingLink(icon = {
                Icon(
                    imageVector = Icons.Filled.Message,
                    contentDescription = stringResource(R.string.all_message)
                )
            }, title = {
                Text(stringResource(R.string.all_message))
            }, onClick = {
                navController.navigate(SaveMyLifeScreenEnum.MessageSettings.name)
            })

            val isLocationSharingEnabled by locationSharingSettingsViewModel.isLocationSharingEnabled.collectAsState()
            SettingSwitch(isChecked = isLocationSharingEnabled, onCheckedChange = {
                locationSharingSettingsViewModel.setIsLocationSharingEnabled(it)            }, icon = {
                Icon(imageVector = Icons.Filled.ShareLocation, contentDescription = stringResource(R.string.location_settings_screen_location_sharing))
            }, title = {
                Text(stringResource(R.string.location_settings_screen_location_sharing))
            })
//            SettingLink(icon = {
//                Icon(
//                    imageVector = Icons.Filled.ShareLocation,
//                    contentDescription = stringResource(R.string.all_location)
//                )
//            }, title = {
//                Text(stringResource(R.string.all_location))
//            }, onClick = {
//                navController.navigate(SaveMyLifeScreenEnum.LocationSettings.name)
//            })
        }
    }


}

