package com.freephoenix888.savemylife.navigation.navGraph

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.freephoenix888.savemylife.navigation.Route
import com.freephoenix888.savemylife.navigation.Screen
import com.freephoenix888.savemylife.ui.composables.screens.MessageCommandsSettingsScreen
import com.freephoenix888.savemylife.ui.composables.screens.MessageSendingIntervalSettingsScreen
import com.freephoenix888.savemylife.ui.composables.screens.MessageSettingsScreen
import com.freephoenix888.savemylife.ui.composables.screens.MessageTemplateSettingsScreen
import com.freephoenix888.savemylife.ui.viewModels.MessageSettingsViewModel

fun NavGraphBuilder.messageSettingsNavGraph(
    navController: NavHostController
) {
    navigation(route = Route.MessageSettings.name, startDestination = Screen.MessageSettings.route) {
        composable(route = Screen.MessageSettings.route) {
            val messageSettingsViewModel: MessageSettingsViewModel = hiltViewModel()
            MessageSettingsScreen(messageSettingsViewModel = messageSettingsViewModel, navController = navController)
        }
        composable(route = Screen.MessageTemplateSettings.route) {
            val messageSettingsViewModel: MessageSettingsViewModel = hiltViewModel()
            MessageTemplateSettingsScreen(messageSettingsViewModel = messageSettingsViewModel, navController = navController)
        }
        composable(route = Screen.MessageSendingIntervalSettings.route) {
            val messageSettingsViewModel: MessageSettingsViewModel = hiltViewModel()
            MessageSendingIntervalSettingsScreen(messageSettingsViewModel = messageSettingsViewModel, navController = navController)
        }
        composable(route = Screen.MessageCommandsSettings.route) {
            val messageSettingsViewModel: MessageSettingsViewModel = hiltViewModel()
            MessageCommandsSettingsScreen(messageSettingsViewModel = messageSettingsViewModel, navController = navController)
        }
    }
}