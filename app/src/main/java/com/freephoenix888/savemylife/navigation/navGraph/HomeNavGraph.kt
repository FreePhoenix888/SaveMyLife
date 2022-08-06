package com.freephoenix888.savemylife.navigation.navGraph

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.freephoenix888.savemylife.navigation.Route
import com.freephoenix888.savemylife.navigation.Screen
import com.freephoenix888.savemylife.ui.composables.screens.HomeScreen
import com.freephoenix888.savemylife.ui.viewModels.SaveMyLifeViewModel

fun NavGraphBuilder.homeNavGraph(
    navController: NavHostController
) {
    navigation(startDestination = Screen.Home.route,
    route = Route.Home.name) {
        composable(Screen.Home.route) {
            val saveMyLifeViewModel: SaveMyLifeViewModel = hiltViewModel()
            HomeScreen(
                saveMyLifeViewModel = saveMyLifeViewModel,
                navController = navController
            )
        }

    }
}