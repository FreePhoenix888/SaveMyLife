package com.freephoenix888.savemylife.navigation.navGraph

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import com.freephoenix888.savemylife.constants.DeepLinks
import com.freephoenix888.savemylife.navigation.Route
import com.freephoenix888.savemylife.ui.composables.screens.AlarmButtonScreen
import com.freephoenix888.savemylife.ui.viewModels.SaveMyLifeViewModel

@Composable
fun SaveMyLifeNavGraph(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Route.Home.name, route = Route.Root.name){
        homeNavGraph(navController = navController)
        settingsNavGraph(navController = navController)
        composable(
            route = Route.AlarmButton.name,
            deepLinks = listOf(navDeepLink {
                uriPattern = DeepLinks.alarmButton.toString()
            })
        ) {
            val saveMyLifeViewModel: SaveMyLifeViewModel = hiltViewModel()
            AlarmButtonScreen(saveMyLifeViewModel = saveMyLifeViewModel)
        }    }
}