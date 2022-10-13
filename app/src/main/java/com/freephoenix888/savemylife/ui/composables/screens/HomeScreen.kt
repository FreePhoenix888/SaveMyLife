package com.freephoenix888.savemylife.ui.composables.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.freephoenix888.savemylife.R
import com.freephoenix888.savemylife.ui.SaveMyLifeScreenEnum
import com.freephoenix888.savemylife.ui.viewModels.SaveMyLifeViewModel


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("BatteryLife")
@Composable
fun HomeScreen(
    navController: NavController,
    saveMyLifeViewModel: SaveMyLifeViewModel = viewModel()
) {
    val context = LocalContext.current
    val isMainServiceEnabled by saveMyLifeViewModel.isMainServiceEnabled.collectAsState(initial = false)
    val isFirstAppLaunch by saveMyLifeViewModel.isFirstAppLaunch.collectAsState(initial = false)

    var isSettingsHintDialogOpened by remember { mutableStateOf(isFirstAppLaunch)}
    if(isSettingsHintDialogOpened) {
        AlertDialog(onDismissRequest = { isSettingsHintDialogOpened = false }, title = {
            Text(stringResource(R.string.home_screen_switch_app_state))
        }, text={
            Text(stringResource(R.string.home_screen_do_not_forget_to_adjust_settings_for_you_before_using_the_app))

        }, confirmButton = {
            Button(onClick = { isSettingsHintDialogOpened = false}) {
                Text(stringResource(R.string.all_ok))
            }
        })
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("SaveMyLife")
                },
                actions = {
                    IconButton(
                        onClick = {
                            navController.navigate(SaveMyLifeScreenEnum.Settings.name)
                        },
                        modifier = Modifier
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = stringResource(R.string.all_settings)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) {
            Button(
                onClick = {
                    saveMyLifeViewModel.switchIsMainServiceEnabled()
                },
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = if (isMainServiceEnabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error),
                modifier = Modifier
                    .size(150.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.PowerSettingsNew,
                    contentDescription = stringResource(R.string.home_screen_switch_app_state),
                    Modifier.fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = if(isMainServiceEnabled)  stringResource(R.string.home_screen_app_state_enabled) else stringResource(R.string.home_screen_app_state_disabled),
            )
        }
    }

}