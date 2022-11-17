package com.freephoenix888.savemylife.navigation.navGraph

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavigatorProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.createGraph
import com.freephoenix888.savemylife.navigation.Route
import com.freephoenix888.savemylife.ui.composables.screens.HomeScreen
import com.freephoenix888.savemylife.ui.viewModels.SaveMyLifeViewModel

@Composable
fun SaveMyLifeNavGraph(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Route.Home.HomeRoot.name, route = Route.Home.HomeRoute.name){
        composable(Route.Home.HomeRoot.name) {
            val saveMyLifeViewModel: SaveMyLifeViewModel = hiltViewModel()
            HomeScreen(
                saveMyLifeViewModel = saveMyLifeViewModel,
                navController = navController
            )
        }
        settingsNavGraph(navController = navController)
    }
}