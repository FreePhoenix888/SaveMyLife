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
import com.freephoenix888.savemylife.ui.viewModels.LocationSettingsViewModel
import com.freephoenix888.savemylife.ui.viewModels.MessageSettingsViewModel
import com.freephoenix888.savemylife.ui.viewModels.PhoneNumberSettingsViewModel
import com.freephoenix888.savemylife.ui.viewModels.SaveMyLifeViewModel

@Composable
fun SaveMyLifeAppComposable() {
    SaveMyLifeTheme {
        SaveMyLifeNavHostComposable()
    }
}

@Composable
private fun SaveMyLifeNavHostComposable(
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = SaveMyLifeScreenEnum.Home.name,
    ) {
        composable(SaveMyLifeScreenEnum.Home.name) {
            val saveMyLifeViewModel: SaveMyLifeViewModel = hiltViewModel()
            HomeScreenComposable(
                saveMyLifeViewModel = saveMyLifeViewModel,
                navController = navController
            )
        }
        composable(SaveMyLifeScreenEnum.Settings.name) {
            SettingsScreenComposable(navController = navController)
        }
        composable(SaveMyLifeScreenEnum.SmsSettings.name) {
            val phoneNumberSettingsViewModel: PhoneNumberSettingsViewModel =
                hiltViewModel()
            PhoneNumbersScreenComposable(
                phoneNumberSettingsViewModel = phoneNumberSettingsViewModel,
                navController = navController
            )
        }
        composable(SaveMyLifeScreenEnum.MessageSettings.name) {
            val emergencyMessageSettingsViewModel: MessageSettingsViewModel = hiltViewModel()
            MessageSettingsScreenComposable(messageSettingsViewModel = emergencyMessageSettingsViewModel, navController = navController)
        }
        composable(SaveMyLifeScreenEnum.LocationSettings.name) {
            val locationSettingsViewModel: LocationSettingsViewModel = hiltViewModel()
            LocationSettingsScreenComposable(locationSettingsViewModel = locationSettingsViewModel, navController = navController)
        }
        composable(
            route = SaveMyLifeScreenEnum.DangerButton.name,
            deepLinks = listOf(navDeepLink {
                uriPattern = "${Constants.APP_URI}/screen/${SaveMyLifeScreenEnum.DangerButton.name}"
            })
        ) {
            val saveMyLifeViewModel: SaveMyLifeViewModel = hiltViewModel()
            DangerButtonScreenComposable(saveMyLifeViewModel = saveMyLifeViewModel)
        }
    }
}

@Preview
@Composable
private fun SaveMyLifeAppPreview() {
    SaveMyLifeAppComposable()
}