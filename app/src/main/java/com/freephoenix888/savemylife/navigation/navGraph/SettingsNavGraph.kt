package com.freephoenix888.savemylife.navigation.navGraph

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.freephoenix888.savemylife.navigation.Route
import com.freephoenix888.savemylife.navigation.Screen
import com.freephoenix888.savemylife.ui.composables.screens.LocationSettingsScreen
import com.freephoenix888.savemylife.ui.composables.screens.PhoneNumbersScreen
import com.freephoenix888.savemylife.ui.composables.screens.SettingsScreen
import com.freephoenix888.savemylife.ui.viewModels.LocationSharingSettingsViewModel
import com.freephoenix888.savemylife.ui.viewModels.PhoneNumberSettingsViewModel

fun NavGraphBuilder.settingsNavGraph(
    navController: NavHostController
) {
    navigation(route = Route.Settings.name, startDestination = Screen.Settings.route) {
        composable(Screen.Settings.route) {
            val locationSharingSettingsViewModel: LocationSharingSettingsViewModel = hiltViewModel()
            SettingsScreen(navController = navController,
                locationSharingSettingsViewModel = locationSharingSettingsViewModel)
        }
        messageSettingsNavGraph(navController = navController)

        composable(Screen.PhoneNumbersSettings.route) {
            val phoneNumberSettingsViewModel: PhoneNumberSettingsViewModel =
                hiltViewModel()
            PhoneNumbersScreen(
                phoneNumberSettingsViewModel = phoneNumberSettingsViewModel,
                navController = navController
            )
        }

        composable(Screen.LocationSharingSettings.route) {
            val locationSharingSettingsViewModel: LocationSharingSettingsViewModel = hiltViewModel()
            LocationSettingsScreen(
                locationSharingSettingsViewModel = locationSharingSettingsViewModel,
                navController = navController
            )
        }


    }

}
