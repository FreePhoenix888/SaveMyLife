package com.freephoenix888.savemylife.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import com.freephoenix888.savemylife.constants.Constants
import com.freephoenix888.savemylife.ui.SaveMyLifeScreenEnum
import com.freephoenix888.savemylife.ui.composables.screens.DangerButtonScreenComposable
import com.freephoenix888.savemylife.ui.composables.screens.EmergencyContactsSettingsScreenComposable
import com.freephoenix888.savemylife.ui.composables.screens.HomeScreenComposable
import com.freephoenix888.savemylife.ui.composables.screens.MessageSettingsScreenComposable
import com.freephoenix888.savemylife.ui.theme.SaveMyLifeTheme
import com.freephoenix888.savemylife.ui.viewModels.EmergencyContactViewModel
import com.freephoenix888.savemylife.ui.viewModels.EmergencyMessageViewModel
import com.freephoenix888.savemylife.ui.viewModels.LocationViewModel
import com.freephoenix888.savemylife.ui.viewModels.SaveMyLifeViewModel

@Composable
fun SaveMyLifeApp() {
    SaveMyLifeTheme {
        val emergencyContactViewModel: EmergencyContactViewModel = viewModel()
        val emergencyMessageViewModel: EmergencyMessageViewModel = viewModel()
        val locationViewModel: LocationViewModel = viewModel()
        val saveMyLifeViewModel: SaveMyLifeViewModel = viewModel()
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
            HomeScreenComposable(saveMyLifeViewModel = saveMyLifeViewModel, navController = navController)
        }
        composable(SaveMyLifeScreenEnum.Settings.name) {
            SettingsScreenComposable(navController = navController)
        }
        composable(SaveMyLifeScreenEnum.ContactsSettings.name) {
            val emergencyContactViewModel: EmergencyContactViewModel = hiltViewModel()
            EmergencyContactsSettingsScreenComposable(emergencyContactViewModel = emergencyContactViewModel)
        }
        composable(SaveMyLifeScreenEnum.MessagesSettings.name) {
            val emergencyMessageViewModel: EmergencyMessageViewModel = hiltViewModel()
            MessageSettingsScreenComposable(emergencyMessageViewModel = emergencyMessageViewModel)
        }
        composable(SaveMyLifeScreenEnum.LocationSettings.name) {
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