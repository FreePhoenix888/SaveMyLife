package com.freephoenix888.savemylife.navigation.navGraph

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.freephoenix888.savemylife.navigation.NavigationDestination
import com.freephoenix888.savemylife.ui.composables.screens.MessageCommandsSettingsScreen
import com.freephoenix888.savemylife.ui.composables.screens.MessageSendingIntervalSettingsScreen
import com.freephoenix888.savemylife.ui.composables.screens.MessageSettingsScreen
import com.freephoenix888.savemylife.ui.composables.screens.MessageTemplateSettingsScreen
import com.freephoenix888.savemylife.ui.viewModels.MessageSettingsViewModel

fun NavGraphBuilder.messageSettingsNavGraph(
    navController: NavHostController
) {
    navigation(route = "Message Settings Route", startDestination = NavigationDestination.MessageSettings.name) {
        composable(route = NavigationDestination.MessageSettings.name) {
            val messageSettingsViewModel: MessageSettingsViewModel = hiltViewModel()
            MessageSettingsScreen(messageSettingsViewModel = messageSettingsViewModel, navController = navController)
        }
        composable(route = NavigationDestination.MessageTemplateSettings.name) {
            val messageSettingsViewModel: MessageSettingsViewModel = hiltViewModel()
            MessageTemplateSettingsScreen(messageSettingsViewModel = messageSettingsViewModel, navController = navController)
        }
        composable(route = NavigationDestination.MessageSendingIntervalSettings.name) {
            val messageSettingsViewModel: MessageSettingsViewModel = hiltViewModel()
            MessageSendingIntervalSettingsScreen(messageSettingsViewModel = messageSettingsViewModel, navController = navController)
        }
        composable(route = NavigationDestination.MessageCommandsSettings.name) {
            val messageSettingsViewModel: MessageSettingsViewModel = hiltViewModel()
            MessageCommandsSettingsScreen(messageSettingsViewModel = messageSettingsViewModel, navController = navController)
        }
    }
}