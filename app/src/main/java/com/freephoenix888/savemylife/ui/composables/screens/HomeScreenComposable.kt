package com.freephoenix888.savemylife.ui.composables.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.freephoenix888.savemylife.ui.SaveMyLifeScreenEnum
import com.freephoenix888.savemylife.ui.viewModels.SaveMyLifeViewModel

@Composable
fun HomeScreenComposable(
    navController: NavController,
    saveMyLifeViewModel: SaveMyLifeViewModel = viewModel()
) {
    val isMainServiceEnabled by saveMyLifeViewModel.isMainServiceEnabled.collectAsState(initial = false)
    HomeScreenBodyComposable(
        onSettingsButtonClick = {
            navController.navigate(SaveMyLifeScreenEnum.Settings.name)
        },
        isMainServiceEnabled = isMainServiceEnabled,
        onSwitchIsMainServiceEnabled = {
            saveMyLifeViewModel.switchIsMainServiceEnabled()
        },
    )
}

@Composable
fun HomeScreenBodyComposable(
    isMainServiceEnabled: Boolean,
    onSwitchIsMainServiceEnabled: () -> Unit,
    onSettingsButtonClick: () -> Unit,
) {
    Scaffold { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) {
            Button(
                onClick = onSwitchIsMainServiceEnabled,
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(backgroundColor = if(isMainServiceEnabled) MaterialTheme.colors.primary else MaterialTheme.colors.error),
                modifier = Modifier
                    .size(150.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.PowerSettingsNew,
                    contentDescription = "Switch app state",
                    Modifier.fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = buildAnnotatedString {
                    append("SaveMyLife is ")
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Black,
                            color = if (isMainServiceEnabled) MaterialTheme.colors.primary else MaterialTheme.colors.error
                        )
                    ) {
                        append((if (isMainServiceEnabled) "enabled" else "disabled").uppercase())
                    }
                },
            )
//            Button(
//                onClick = onSettingsButtonClick,
//                modifier = Modifier
//                    .padding(bottom = 32.dp)
//            ) {
//                Icon(imageVector = Icons.Filled.Settings, contentDescription = "Settings")
//                Text("Settings")
//            }
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 480)
@Composable
private fun HomeScreenComposablePreview() {
    val context = LocalContext.current
    var dangerModeState by remember { mutableStateOf(false) }
    HomeScreenBodyComposable(isMainServiceEnabled = dangerModeState,
        onSwitchIsMainServiceEnabled = {
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