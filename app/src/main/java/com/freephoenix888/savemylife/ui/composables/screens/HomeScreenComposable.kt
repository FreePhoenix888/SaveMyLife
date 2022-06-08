package com.freephoenix888.savemylife.ui.composables.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.freephoenix888.savemylife.ui.SaveMyLifeScreenEnum
import com.freephoenix888.savemylife.ui.viewModels.SaveMyLifeViewModel

@Composable
fun HomeScreenComposable(
    navController: NavController,
    saveMyLifeViewModel: SaveMyLifeViewModel = viewModel()
) {
    val dangerModeState by saveMyLifeViewModel.isMainServiceEnabled.collectAsState(initial = false)
    HomeScreenBodyComposable(
        onSettingsButtonClick = {
            navController.navigate(SaveMyLifeScreenEnum.Settings.name)
        },
        dangerModeState = dangerModeState,
        onSwitchMainServiceStateButtonClick = {
            saveMyLifeViewModel.setIsMainServiceEnabled()
        },
    )
}

@Composable
fun HomeScreenBodyComposable(
    dangerModeState: Boolean,
    onSwitchMainServiceStateButtonClick: () -> Unit,
    onSettingsButtonClick: () -> Unit,
) {
    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) {
            Button(
                onClick = onSwitchMainServiceStateButtonClick,
                shape = MaterialTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(backgroundColor = if(dangerModeState) MaterialTheme.colors.primary else MaterialTheme.colors.error),
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(100.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.PowerSettingsNew,
                    contentDescription = "Switch app state"
                )
            }
            Button(
                onClick = onSettingsButtonClick,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 32.dp)
            ) {
                Icon(imageVector = Icons.Filled.Settings, contentDescription = "Settings")
                Text("Settings")
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 480)
@Composable
private fun HomeScreenComposablePreview() {
    val context = LocalContext.current
    var dangerModeState by remember { mutableStateOf(false) }
    HomeScreenBodyComposable(dangerModeState = dangerModeState,
        onSwitchMainServiceStateButtonClick = {
            dangerModeState = !dangerModeState

        },
        onSettingsButtonClick = {
            Toast.makeText(
                context,
                "Settings button clicked",
                Toast.LENGTH_SHORT
            ).show()
        })
}