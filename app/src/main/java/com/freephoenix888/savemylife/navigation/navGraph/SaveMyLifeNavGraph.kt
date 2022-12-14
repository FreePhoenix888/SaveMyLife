package com.freephoenix888.savemylife.navigation.navGraph

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.freephoenix888.savemylife.navigation.NavigationDestination
import com.freephoenix888.savemylife.ui.composables.screens.DangerModeActivationConfirmationScreen
import com.freephoenix888.savemylife.ui.composables.screens.HomeScreen
import com.freephoenix888.savemylife.ui.viewModels.SaveMyLifeViewModel

@Composable
fun SaveMyLifeNavGraph(
    navHostController: NavHostController
) {
    NavHost(navController = navHostController, startDestination = NavigationDestination.Home.name, route = "Root Route"){
        composable(NavigationDestination.Home.name) {
            val saveMyLifeViewModel: SaveMyLifeViewModel = hiltViewModel()
            HomeScreen(
                saveMyLifeViewModel = saveMyLifeViewModel,
                navController = navHostController
            )
        }
        settingsNavGraph(navController = navHostController)
        composable(NavigationDestination.DangerModeActivationConfirmation.name) {
            val saveMyLifeViewModel: SaveMyLifeViewModel = hiltViewModel()
            DangerModeActivationConfirmationScreen(navController = navHostController, saveMyLifeViewModel = saveMyLifeViewModel)
        }
    }
}