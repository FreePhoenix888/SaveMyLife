package com.freephoenix888.savemylife.ui.composables.screens

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.freephoenix888.savemylife.ui.viewModels.SaveMyLifeViewModel
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.Popup
import com.freephoenix888.savemylife.R
import com.freephoenix888.savemylife.constants.Constants
import com.freephoenix888.savemylife.navigation.Route
import com.freephoenix888.savemylife.ui.composables.RequestPermission
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.permissions.*
import kotlinx.coroutines.delay


@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class
)
@SuppressLint("BatteryLife")
@Composable
fun HomeScreen(
    navController: NavController,
    saveMyLifeViewModel: SaveMyLifeViewModel = viewModel()
) {
    val context = LocalContext.current
    val isMainServiceEnabled by saveMyLifeViewModel.isMainServiceEnabled.collectAsState(initial = false)
    val isFirstAppLaunch by saveMyLifeViewModel.isFirstAppLaunch.collectAsState(initial = false)
    val isAlarmModeEnabled by saveMyLifeViewModel.isAlarmModeEnabled.collectAsState(initial = false)

    val notificationsPermissionState = rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
    if(!notificationsPermissionState.status.isGranted) {
        RequestPermission(
            permissionState = notificationsPermissionState,
            permissionHumanReadableName = "Notifications",
            description = stringResource(R.string.home_screen_post_notifications_permission_request)
        )
        return
    }

    if(Build.VERSION.SDK_INT >= 23 && !Settings.canDrawOverlays(context)) {
        val overlayPermissionState = rememberPermissionState(permission = Manifest.permission.SYSTEM_ALERT_WINDOW)
        RequestPermission(
            permissionState = overlayPermissionState,
            permissionRequestHandler = { context, permissionState, isFirstRequest ->
                val intent = Intent(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:${Constants.APP_PACKAGE_NAME}")
                )
                context.startActivity(intent)
            },
            permissionHumanReadableName = "On top of other applications",
            description = "Overlay permission is used to show you an overlay window when alarm mode is enabled to let you cancel it if you did it unintentionally",
        )
        return
    }

//    var isSettingsHintDialogOpened by remember { mutableStateOf(isFirstAppLaunch) }
//    if (isSettingsHintDialogOpened) {
//        AlertDialog(onDismissRequest = { isSettingsHintDialogOpened = false }, title = {
//            Text(stringResource(R.string.home_screen_switch_app_state))
//        }, text = {
//            Text(stringResource(R.string.home_screen_do_not_forget_to_adjust_settings_for_you_before_using_the_app))
//
//        }, confirmButton = {
//            Button(onClick = { isSettingsHintDialogOpened = false }) {
//                Text(stringResource(R.string.all_ok))
//            }
//        })
//    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("SaveMyLife")
                },
                actions = {
                    IconButton(
                        onClick = {
                            navController.navigate(Route.Home.Settings.SettingsRoot.name)
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
            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
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
                Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = if (isMainServiceEnabled) stringResource(R.string.home_screen_app_state_enabled) else stringResource(
                            R.string.home_screen_app_state_disabled
                        ),
                    )
                }
            }

            Spacer(modifier = Modifier.height(50.dp))


            var isAlarmModePopupWarningEnabled by remember {
                mutableStateOf(isAlarmModeEnabled)
            }
            var alarmModePopupWarningTimer by remember {
                mutableStateOf(5)
            }

            var isAlarmModeEnabledSwitchState by remember {
                mutableStateOf(isAlarmModeEnabled)
            }

            LaunchedEffect(key1 = isAlarmModeEnabledSwitchState, block = {
                if(!isAlarmModeEnabledSwitchState) {
                    saveMyLifeViewModel.setIsAlarmModeEnabled(false)
                    isAlarmModePopupWarningEnabled = false
                    alarmModePopupWarningTimer = 5
                } else  {
                    isAlarmModePopupWarningEnabled = true
                    while (alarmModePopupWarningTimer != 0) {
                        delay(1000)
                        --alarmModePopupWarningTimer

                    }
                    alarmModePopupWarningTimer = 5
                    saveMyLifeViewModel.setIsAlarmModeEnabled(true)
                    isAlarmModePopupWarningEnabled = false
                }
            })

           Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier
               .clickable {
                   isAlarmModeEnabledSwitchState = !isAlarmModeEnabledSwitchState
               }
               .background(
                   color = MaterialTheme.colorScheme.error.copy(alpha = 0.3f),
                   shape = RoundedCornerShape(50)
               )
               .padding(30.dp)){
               Text("Alarm mode")
               Switch(checked = isAlarmModeEnabledSwitchState, onCheckedChange = {
                   isAlarmModeEnabledSwitchState = it
               }, enabled = isMainServiceEnabled, thumbContent = {
                   Icon(imageVector = Icons.Filled.Campaign, contentDescription = "Alarm mode")
               })
               var isAlarmModeAlertDialogInfoEnabled by remember {
                   mutableStateOf(false)
               }
               if(isAlarmModeAlertDialogInfoEnabled) {
                   AlertDialog(onDismissRequest = {
                       isAlarmModeAlertDialogInfoEnabled = false
                   }, confirmButton = {},
                   icon = {
                       Icon(imageVector = Icons.Filled.Campaign, contentDescription = "Alarm mode info")
                   },
                   title = {
                       Text("Alarm mode")
                   },
                   text = {
                       Column() {

                           FlowRow {
                               Text("Enable alarm mode if you are in danger and SaveMyLife will send emergency messages to your emergency phone numbers")
                               Text("It is not all what SaveMyLife can do. Go to settings and configure SaveMyLife for you")
                               Text("Location sharing", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold,modifier = Modifier.clickable(
                                   onClick = {
                                       navController.navigate(Route.Home.Settings.LocationSharingSettings.name)
                                   }
                               ))
                               Text("Enable location sharing to send your location url in the emergency message")
                               Text("Message commands", style = MaterialTheme.typography.headlineSmall,modifier = Modifier.clickable(
                                   onClick = {
                                       navController.navigate(Route.Home.Settings.MessageSettings.MessageCommandsSettings.name)
                                   }
                               ))
                               Text("Enable message command to let your emergency contacts get specific information about you by sending message commands to you")
                           }
                       }
                   })
               }
               IconButton(onClick = {
                   isAlarmModeAlertDialogInfoEnabled = true
               }) {
                   Icon(imageVector = Icons.Filled.Info, contentDescription = "Alarm mode info")
               }
           }

            if(isAlarmModePopupWarningEnabled) {
                Popup(alignment = Alignment.Center) {
                    Column(modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Alarm mode will be enabled in $alarmModePopupWarningTimer")
                        Button(onClick = {
                            isAlarmModeEnabledSwitchState = false
                        }) {
                            Text("Cancel")
                        }
                    }
                }
            }
        }
    }

}