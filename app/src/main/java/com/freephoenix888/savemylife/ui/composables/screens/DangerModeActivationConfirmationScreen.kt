package com.freephoenix888.savemylife.ui.composables.screens

import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.os.*
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.freephoenix888.savemylife.ui.viewModels.SaveMyLifeViewModel
import kotlinx.coroutines.delay


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DangerModeActivationConfirmationScreen(
    navController: NavController,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    saveMyLifeViewModel: SaveMyLifeViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Danger Mode Confirmation")
                })
        }
    ) { innerPadding ->
        val context = LocalContext.current as ComponentActivity

        DisposableEffect(lifecycleOwner) {
            onDispose {
                if (Build.VERSION.SDK_INT >= 27) {
                    context.setShowWhenLocked(false)
                    context.setTurnScreenOn(false)
                } else {
                    @Suppress("DEPRECATION")
                    context.window.clearFlags(
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or
                                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                    )
                }
            }
        }

        val vibrator = if (Build.VERSION.SDK_INT >= 31) {
            val vibratorManager =
                context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(VIBRATOR_SERVICE) as Vibrator
        }

        val vibrate: (milliseconds: Long) -> Unit = { milliseconds ->
            if (Build.VERSION.SDK_INT >= 26) {
                vibrator.vibrate(
                    VibrationEffect.createOneShot(milliseconds, VibrationEffect.DEFAULT_AMPLITUDE),
                )
            } else {
                @Suppress("DEPRECATION")
                vibrator.vibrate(milliseconds)
            }
        }

        var isActionConfirmed by remember {
            mutableStateOf(false)
        }
        val onAction: (isDangerModeEnabled: Boolean) -> Unit = { isDangerModeEnabled ->
            vibrate(if(isDangerModeEnabled) 1000 else 500)
            isActionConfirmed = true
            saveMyLifeViewModel.setIsDangerModeEnabled(isDangerModeEnabled)
            navController.popBackStack()
        }
        var secondsTimer by remember { mutableStateOf(10) }

        LaunchedEffect(Unit) {
            vibrate(1000)
            while (secondsTimer != 0 && !isActionConfirmed) {
                delay(1000)
                --secondsTimer
                if (secondsTimer == 0) {
                    onAction(true)
                }
            }
        }

        var isAlertDialogOnBackIsEnabled by remember {
            mutableStateOf(false)
        }


        val buttonDisable: @Composable () -> Unit = {
            Button(onClick = {
                onAction(false)
            }) {
                Text("Disable")
            }
        }

        val buttonEnable: @Composable () -> Unit = {
            Button(onClick = {
                onAction(true)
            }) {
                Text("Enable")
            }
        }

        if (isAlertDialogOnBackIsEnabled) {
            AlertDialog(onDismissRequest = { isAlertDialogOnBackIsEnabled = false }, title = {
                Text("Danger Mode will be enabled")
            },
                confirmButton = {
                    buttonEnable()
                }, dismissButton = {
                    buttonDisable()
                }, icon = {
                    Icon(imageVector = Icons.Filled.Campaign, contentDescription = "Danger mode")
                })

        }

        BackHandler(enabled = !isActionConfirmed) {
            isAlertDialogOnBackIsEnabled = true
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Danger mode will be enabled in $secondsTimer")
            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    space = 16.dp,
                    alignment = Alignment.CenterHorizontally
                )
            ) {
                buttonDisable()
                buttonEnable()
            }
        }
    }
}




