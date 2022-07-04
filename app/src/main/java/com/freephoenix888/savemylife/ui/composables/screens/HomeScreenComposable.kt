package com.freephoenix888.savemylife.ui.composables.screens

import android.annotation.SuppressLint
import android.content.Context.POWER_SERVICE
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.freephoenix888.savemylife.R
import com.freephoenix888.savemylife.ui.SaveMyLifeScreenEnum
import com.freephoenix888.savemylife.ui.viewModels.SaveMyLifeViewModel


@SuppressLint("BatteryLife")
@Composable
fun HomeScreenComposable(
    navController: NavController,
    saveMyLifeViewModel: SaveMyLifeViewModel = viewModel()
) {
    val context = LocalContext.current
    val isMainServiceEnabled by saveMyLifeViewModel.isMainServiceEnabled.collectAsState(initial = false)
    HomeScreenBodyComposable(
        onSettingsButtonClick = {
            navController.navigate(SaveMyLifeScreenEnum.Settings.name)
        },
        isMainServiceEnabled = isMainServiceEnabled,
        onSwitchIsMainServiceEnabled = {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val intent = Intent()
                val packageName: String = context.packageName
                val pm: PowerManager? = context.getSystemService(POWER_SERVICE) as PowerManager?
                if (pm != null && !pm.isIgnoringBatteryOptimizations(packageName)) {
                    intent.action = Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
                    intent.data = Uri.parse("package:$packageName")
                    context.startActivity(intent)
                }
            }
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
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("SaveMyLife")
                },
                actions = {
                    IconButton(
                        onClick = onSettingsButtonClick,
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
                onClick = onSwitchIsMainServiceEnabled,
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(backgroundColor = if (isMainServiceEnabled) MaterialTheme.colors.primary else MaterialTheme.colors.error),
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
                text = buildAnnotatedString {
                    append("SaveMyLife is ")
                    withStyle(
                        style = SpanStyle(
                            fontWeight = FontWeight.Black,
                            color = if (isMainServiceEnabled) MaterialTheme.colors.primary else MaterialTheme.colors.error
                        )
                    ) {
                        append(
                            (if (isMainServiceEnabled) stringResource(R.string.home_screen_app_state_enabled) else stringResource(
                                R.string.home_screen_app_state_disabled
                            )).uppercase()
                        )
                    }
                },
            )
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