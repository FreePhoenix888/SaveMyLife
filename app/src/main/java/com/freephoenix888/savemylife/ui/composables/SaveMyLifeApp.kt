package com.freephoenix888.savemylife.ui.composables

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.freephoenix888.savemylife.navigation.navGraph.SaveMyLifeNavGraph
import com.freephoenix888.savemylife.ui.theme.SaveMyLifeTheme

@Composable
fun SaveMyLifeApp() {
    SaveMyLifeTheme {
        SaveMyLifeNavGraph(navController = rememberNavController())
    }
}

//@Composable
//private fun SaveMyLifeNavHost(
//) {
//    val navController = rememberNavController()
//    NavHost(
//        navController = navController,
//        startDestination = SaveMyLifeScreenEnum.Home.name,
//    ) {
//        composable(SaveMyLifeScreenEnum.Home.name) {
//            val saveMyLifeViewModel: SaveMyLifeViewModel = hiltViewModel()
//            HomeScreen(
//                saveMyLifeViewModel = saveMyLifeViewModel,
//                navController = navController
//            )
//        }
//        navigation(startDestination = SaveMyLifeScreenEnum.Settings.name, route = SaveMyLifeScreenEnum.Settings.name) {
//            composable(SaveMyLifeScreenEnum.Settings.name) {
//                val locationSharingSettingsViewModel: LocationSharingSettingsViewModel = hiltViewModel()
//                SettingsScreen(navController = navController,
//                    locationSharingSettingsViewModel = locationSharingSettingsViewModel)
//            }
//            composable(SaveMyLifeScreenEnum.MessageSettings.name) {
//                val emergencyMessageSettingsViewModel: MessageSettingsViewModel = hiltViewModel()
//                MessageSettingsScreen(messageSettingsViewModel = emergencyMessageSettingsViewModel, navController = navController)
//            }
//
//        }
//        composable(SaveMyLifeScreenEnum.SmsSettings.name) {
//            val phoneNumberSettingsViewModel: PhoneNumberSettingsViewModel =
//                hiltViewModel()
//            PhoneNumbersScreen(
//                phoneNumberSettingsViewModel = phoneNumberSettingsViewModel,
//                navController = navController
//            )
//        }
//
//        composable(SaveMyLifeScreenEnum.LocationSettings.name) {
//            val locationSharingSettingsViewModel: LocationSharingSettingsViewModel = hiltViewModel()
//            LocationSettingsScreen(locationSharingSettingsViewModel = locationSharingSettingsViewModel, navController = navController)
//        }
//        composable(
//            route = SaveMyLifeScreenEnum.AlarmButton.name,
//            deepLinks = listOf(navDeepLink {
//                uriPattern = "${Constants.APP_URI}/screen/${SaveMyLifeScreenEnum.AlarmButton.name}"
//            })
//        ) {
//            val saveMyLifeViewModel: SaveMyLifeViewModel = hiltViewModel()
//            AlarmButtonScreen(saveMyLifeViewModel = saveMyLifeViewModel)
//        }
//        composable(route = SaveMyLifeScreenEnum.MessageTemplateSettings.name) {
//            val messageSettingsViewModel: MessageSettingsViewModel = hiltViewModel()
//            MessageTemplateSettingsScreen(messageSettingsViewModel = messageSettingsViewModel)
//        }
//    }
//}

@Preview
@Composable
private fun SaveMyLifeAppPreview() {
    SaveMyLifeApp()
}