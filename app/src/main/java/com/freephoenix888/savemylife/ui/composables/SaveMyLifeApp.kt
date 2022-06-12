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
import com.freephoenix888.savemylife.ui.viewModels.MessageViewModel
import com.freephoenix888.savemylife.ui.viewModels.LocationViewModel
import com.freephoenix888.savemylife.ui.viewModels.PhoneNumberViewModel
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
            HomeScreenComposable(
                saveMyLifeViewModel = saveMyLifeViewModel,
                navController = navController
            )
        }
        composable(SaveMyLifeScreenEnum.Settings.name) {
            SettingsScreenComposable(navController = navController)
        }
        composable(SaveMyLifeScreenEnum.PhoneNumber.name) {
            val phoneNumberViewModel: PhoneNumberViewModel =
                hiltViewModel()
            PhoneNumbersScreenComposable(
                phoneNumberViewModel = phoneNumberViewModel
            )
        }
        composable(SaveMyLifeScreenEnum.Message.name) {
            val emergencyMessageViewModel: MessageViewModel = hiltViewModel()
            MessageSettingsScreenComposable(emergencyMessageViewModel = emergencyMessageViewModel)
        }
        composable(SaveMyLifeScreenEnum.Location.name) {
            val locationViewModel: LocationViewModel = hiltViewModel()
            LocationSettingsScreenComposable(locationViewModel = locationViewModel)
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
    SaveMyLifeApp()
}