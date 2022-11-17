package com.freephoenix888.savemylife.navigation.navGraph

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.freephoenix888.savemylife.navigation.Route
import com.freephoenix888.savemylife.ui.composables.screens.LocationSettingsScreen
import com.freephoenix888.savemylife.ui.composables.screens.PhoneNumbersScreen
import com.freephoenix888.savemylife.ui.composables.screens.SettingsScreen
import com.freephoenix888.savemylife.ui.viewModels.LocationSharingSettingsViewModel
import com.freephoenix888.savemylife.ui.viewModels.PhoneNumberSettingsViewModel

fun NavGraphBuilder.settingsNavGraph(
    navController: NavHostController
) {
    navigation(route = Route.Home.Settings.SettingsRoute.name, startDestination = Route.Home.Settings.SettingsRoot.name) {
        composable(Route.Home.Settings.SettingsRoot.name) {
            val locationSharingSettingsViewModel: LocationSharingSettingsViewModel = hiltViewModel()
            SettingsScreen(navController = navController,
                locationSharingSettingsViewModel = locationSharingSettingsViewModel)
        }
        messageSettingsNavGraph(navController = navController)

        composable(Route.Home.Settings.PhoneNumbersSettings.name) {
            val phoneNumberSettingsViewModel: PhoneNumberSettingsViewModel =
                hiltViewModel()
            PhoneNumbersScreen(
                phoneNumberSettingsViewModel = phoneNumberSettingsViewModel,
                navController = navController
            )
        }

        composable(Route.Home.Settings.LocationSharingSettings.name) {
            val locationSharingSettingsViewModel: LocationSharingSettingsViewModel = hiltViewModel()
            LocationSettingsScreen(
                locationSharingSettingsViewModel = locationSharingSettingsViewModel,
                navController = navController
            )
        }


    }

}
