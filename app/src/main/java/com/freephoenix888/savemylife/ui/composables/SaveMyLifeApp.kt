package com.freephoenix888.savemylife.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import com.freephoenix888.savemylife.constants.Constants
import com.freephoenix888.savemylife.ui.SaveMyLifeScreenEnum
import com.freephoenix888.savemylife.ui.composables.screens.*
import com.freephoenix888.savemylife.ui.theme.SaveMyLifeTheme
import com.freephoenix888.savemylife.ui.viewModels.LocationSharingSettingsViewModel
import com.freephoenix888.savemylife.ui.viewModels.MessageSettingsViewModel
import com.freephoenix888.savemylife.ui.viewModels.PhoneNumberSettingsViewModel
import com.freephoenix888.savemylife.ui.viewModels.SaveMyLifeViewModel

@Composable
fun SaveMyLifeApp() {
    SaveMyLifeTheme {
        SaveMyLifeNavHost()
    }
}

@Composable
private fun SaveMyLifeNavHost(
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = SaveMyLifeScreenEnum.Home.name,
    ) {
        composable(SaveMyLifeScreenEnum.Home.name) {
            val saveMyLifeViewModel: SaveMyLifeViewModel = hiltViewModel()
            HomeScreen(
                saveMyLifeViewModel = saveMyLifeViewModel,
                navController = navController
            )
        }
        composable(SaveMyLifeScreenEnum.Settings.name) {
            val locationSharingSettingsViewModel: LocationSharingSettingsViewModel = hiltViewModel()
            SettingsScreen(navController = navController,
            locationSharingSettingsViewModel = locationSharingSettingsViewModel)
        }
        composable(SaveMyLifeScreenEnum.SmsSettings.name) {
            val phoneNumberSettingsViewModel: PhoneNumberSettingsViewModel =
                hiltViewModel()
            PhoneNumbersScreen(
                phoneNumberSettingsViewModel = phoneNumberSettingsViewModel,
                navController = navController
            )
        }
        composable(SaveMyLifeScreenEnum.MessageSettings.name) {
            val emergencyMessageSettingsViewModel: MessageSettingsViewModel = hiltViewModel()
            MessageSettingsScreen(messageSettingsViewModel = emergencyMessageSettingsViewModel, navController = navController)
        }
        composable(SaveMyLifeScreenEnum.LocationSettings.name) {
            val locationSharingSettingsViewModel: LocationSharingSettingsViewModel = hiltViewModel()
            LocationSettingsScreen(locationSharingSettingsViewModel = locationSharingSettingsViewModel, navController = navController)
        }
        composable(
            route = SaveMyLifeScreenEnum.DangerButton.name,
            deepLinks = listOf(navDeepLink {
                uriPattern = "${Constants.APP_URI}/screen/${SaveMyLifeScreenEnum.DangerButton.name}"
            })
        ) {
            val saveMyLifeViewModel: SaveMyLifeViewModel = hiltViewModel()
            DangerButtonScreen(saveMyLifeViewModel = saveMyLifeViewModel)
        }
    }
}

@Preview
@Composable
private fun SaveMyLifeAppPreview() {
    SaveMyLifeApp()
}