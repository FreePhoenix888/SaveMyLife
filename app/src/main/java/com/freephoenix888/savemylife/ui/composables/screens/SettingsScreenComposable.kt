package com.freephoenix888.savemylife.ui.composables.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.freephoenix888.savemylife.R
import com.freephoenix888.savemylife.navigation.Route
import com.freephoenix888.savemylife.ui.composables.settings.SettingLink
import com.freephoenix888.savemylife.ui.viewModels.LocationSharingSettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
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
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = stringResource(R.string.all_settings)
                    )
                    Text(stringResource(R.string.all_settings))
                }
            },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                })
        },
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
                navController.navigate(Route.Home.Settings.PhoneNumbersSettings.name)
            })
            SettingLink(icon = {
                Icon(
                    imageVector = Icons.Filled.Message,
                    contentDescription = stringResource(R.string.all_message)
                )
            }, title = {
                Text(stringResource(R.string.all_message))
            }, onClick = {
                navController.navigate(Route.Home.Settings.MessageSettings.MessageSettingsRoute.name)
            })

            SettingLink(icon = {
                Icon(
                    imageVector = Icons.Filled.ShareLocation,
                    contentDescription = stringResource(R.string.all_location_sharing)
                )
            }, title = {
                Text(stringResource(R.string.all_location_sharing))
            }, onClick = {
                navController.navigate(Route.Home.Settings.LocationSharingSettings.name)
            })
        }
    }
}

