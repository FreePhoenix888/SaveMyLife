package com.freephoenix888.savemylife.ui.composables.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Timer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.freephoenix888.savemylife.R
import com.freephoenix888.savemylife.enums.MessageCommands
import com.freephoenix888.savemylife.ui.composables.RequestPermission
import com.freephoenix888.savemylife.ui.composables.settings.SettingSwitch
import com.freephoenix888.savemylife.ui.viewModels.MessageSettingsViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MessageCommandsSettingsScreen(messageSettingsViewModel: MessageSettingsViewModel, navController: NavHostController) {
    val receiveSmsPermissionState = rememberPermissionState(android.Manifest.permission.RECEIVE_SMS)
    if(receiveSmsPermissionState.status != PermissionStatus.Granted){
        RequestPermission(permissionState = receiveSmsPermissionState, text = "Receive SMS permissions is required to use message commands.")
        return
    }


    val isMessageCommandsEnabled by messageSettingsViewModel.isMessageCommandsEnabled.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Icon(imageVector = Icons.Filled.Timer, contentDescription = "Message commands")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Message commands")
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                })
        },
        content = { innerPadding ->
            Column(modifier = Modifier
                .padding(innerPadding)
                .padding(dimensionResource(R.dimen.settings_screen_padding)),
            verticalArrangement = Arrangement.spacedBy(8.dp)) {
                SettingSwitch(icon = {
                                     Icon(imageVector = Icons.Filled.Message, contentDescription = "Message commands")
                }, title = {
                                      Text("Message commands")
                }/*,  subtitle = {Text("Allows your emergency contacts to send specific commands to get specific information")}*/, isChecked = isMessageCommandsEnabled, onCheckedChange = { messageSettingsViewModel.setIsMessageCommandsEnabled(it) })

                Row() {
                    Text("Allows your emergency contacts to send specific commands to get specific information.")
                    Text("Commands:", style = MaterialTheme.typography.h5)
                    Column() {
                        for (messageCommand in MessageCommands.values()) {
                            Text(messageCommand.name.lowercase())
                        }
                    }
                    Text(buildAnnotatedString {
                        append("Syntax: ")
                        withStyle(style = SpanStyle(fontFamily = FontFamily.Monospace)) {
                            append("/command")
                        }
                    })
                    Text(buildAnnotatedString {
                        append("Example: ")
                        withStyle(style = SpanStyle(fontFamily = FontFamily.Monospace)) {
                            append("/location")
                        }
                    })
                }
            }

        })

}
