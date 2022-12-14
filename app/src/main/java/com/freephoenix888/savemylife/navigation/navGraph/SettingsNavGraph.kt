package com.freephoenix888.savemylife.navigation.navGraph

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.freephoenix888.savemylife.navigation.NavigationDestination
import com.freephoenix888.savemylife.ui.composables.screens.LocationSettingsScreen
import com.freephoenix888.savemylife.ui.composables.screens.PhoneNumbersScreen
import com.freephoenix888.savemylife.ui.composables.screens.SettingsScreen
import com.freephoenix888.savemylife.ui.viewModels.LocationSharingSettingsViewModel
import com.freephoenix888.savemylife.ui.viewModels.PhoneNumberSettingsViewModel

fun NavGraphBuilder.settingsNavGraph(
    navController: NavController,
    startDestination: NavigationDestination = NavigationDestination.Settings
) {
    navigation(route = "Settings Route", startDestination = startDestination.name) {
        composable(NavigationDestination.Settings.name) {
            val locationSharingSettingsViewModel: LocationSharingSettingsViewModel = hiltViewModel()
            SettingsScreen(navController = navController,
                locationSharingSettingsViewModel = locationSharingSettingsViewModel)
        }
        messageSettingsNavGraph(navController = navController)

        composable(NavigationDestination.PhoneNumbersSettings.name) {
            val phoneNumberSettingsViewModel: PhoneNumberSettingsViewModel =
                hiltViewModel()
            PhoneNumbersScreen(
                phoneNumberSettingsViewModel = phoneNumberSettingsViewModel,
                navController = navController
            )
        }

        composable(NavigationDestination.LocationSharingSettings.name) {
            val locationSharingSettingsViewModel: LocationSharingSettingsViewModel = hiltViewModel()
            LocationSettingsScreen(
                locationSharingSettingsViewModel = locationSharingSettingsViewModel,
                navController = navController
            )
        }


    }

}
