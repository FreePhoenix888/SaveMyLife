package com.freephoenix888.savemylife.ui.composables

import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Power
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.freephoenix888.savemylife.ui.SaveMyLifeScreenEnum
import com.freephoenix888.savemylife.ui.viewModels.ContactsViewModel

@Composable
fun HomeScreenComposable(
    navController: NavController,
    viewModel: ContactsViewModel
) {
    IconButton(onClick = {

    }) {
        Icon(imageVector = Icons.Filled.Power, contentDescription = "Switch app state")
    }
    Button(onClick = { navController.navigate(SaveMyLifeScreenEnum.Settings.name) }) {
        Icon(imageVector = Icons.Filled.Settings, contentDescription = "Settings")
        Text("Settings")
    }
}

@Preview
@Composable
private fun HomeScreenComposablePreview() {

}