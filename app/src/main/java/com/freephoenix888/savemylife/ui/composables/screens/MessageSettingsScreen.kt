package com.freephoenix888.savemylife.ui.composables.screens

import android.Manifest
import android.os.Build
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.freephoenix888.savemylife.R
import com.freephoenix888.savemylife.navigation.Screen
import com.freephoenix888.savemylife.ui.composables.RequestPermission
import com.freephoenix888.savemylife.ui.composables.settings.SettingLink
import com.freephoenix888.savemylife.ui.viewModels.MessageSettingsViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MessageSettingsScreen(
    messageSettingsViewModel: MessageSettingsViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    if(Build.VERSION.SDK_INT >= 31) {
        val scheduleExactPermission = rememberPermissionState(permission = Manifest.permission.SCHEDULE_EXACT_ALARM)
        if(scheduleExactPermission.status != PermissionStatus.Granted){
            RequestPermission(
                permissionState = scheduleExactPermission,
                text = stringResource(R.string.message_settings_screen_schedule_exact_alarm_permission_request)
            )
            return
        }
    }

    val context = LocalContext.current
    val messageTemplate by messageSettingsViewModel.messageTemplate.collectAsState()
    val messageTemplateErrorMessage by messageSettingsViewModel.messageTemplateErrorMessage.collectAsState()
    val sendingIntervalInMinutes by messageSettingsViewModel.sendingInterval.collectAsState()
    val sendingIntervalErrorMessage by messageSettingsViewModel.sendingIntervalErrorMessage.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(
                        imageVector = Icons.Filled.Message,
                        contentDescription = stringResource(R.string.all_message)
                    )
                    Text(stringResource(R.string.all_message))
                }

            },                 navigationIcon = {
                IconButton(onClick = {
                    navController.navigateUp()
                }, content = {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.all_back)
                    )
                })
            })
        },
        content = { innerPadding: PaddingValues ->

            Column(modifier = Modifier.padding(innerPadding)) {
                var isMessageTemplateDialogOpened by remember { mutableStateOf(false) }

                SettingLink(icon = { Icon(imageVector = Icons.Filled.Message, contentDescription = "Message template")} ,title = {Text("Message template")}, onClick = {
                    navController.navigate(Screen.MessageTemplateSettings.route)
                })

                SettingLink(icon = { Icon(imageVector = Icons.Filled.Timer, contentDescription = "Message sending interval")} ,title = {Text("Message sending interval")}, onClick = {
                    navController.navigate(Screen.MessageSendingIntervalSettings.route)
                })

                SettingLink(icon = { Icon(imageVector = Icons.Filled.Timer, contentDescription = "Message commands")} ,title = {Text("Message commands")}, onClick = {
                    navController.navigate(Screen.MessageCommandsSettings.route)
                })
                //                if(isMessageTemplateDialogOpened) {
//                    AlertDialog(onDismissRequest = { isMessageTemplateDialogOpened = false }, title = {
//                        Text(stringResource(R.string.message_settings_screen_message_template))
//                    }, text={
//                        TextFieldWithErorr(textField = {
//                            OutlinedTextField(
//                                value = messageTemplate,
//                                onValueChange = { messageSettingsViewModel.onMessageTemplateChange(it) },
//                                isError = messageTemplateErrorMessage != null
//                            )
//
//                        }, error = messageTemplateErrorMessage?.let {
//                            return@let {TextFieldError(error = it)}
//                        })
//
//                    }, dismissButton = {
//                        Button(onClick = {
//                            isMessageTemplateDialogOpened = false
//                        }) {
//                            Text(stringResource(R.string.all_cancel))
//                        }
//                    }, confirmButton = {
//                        Button(onClick = {
//                            messageSettingsViewModel.submitMessageTemplate()
//                            isMessageTemplateDialogOpened = false
//                        }, enabled = messageTemplateErrorMessage == null) {
//                            Text(stringResource(R.string.all_save))
//                        }
//                    })
//                }

//                var isMessageSendingIntervalDialogOpened by remember { mutableStateOf(false) }
//                Row() {
//                    SettingsMenuLink(icon = {
//                        Icon(
//                            imageVector = Icons.Filled.Timer,
//                            contentDescription = stringResource(R.string.message_settings_screen_sending_interval)
//                        )
//                    },title = {
//                        Text(stringResource(R.string.message_settings_screen_sending_interval))
//                    }, onClick = {
//                        isMessageSendingIntervalDialogOpened = true
//                    })
//                    IconButton(onClick = { /*TODO*/ }) {
//                        Icon(imageVector = Icons.Filled.Info, contentDescription = "Message template info")
//                    }
//                }
//                if(isMessageSendingIntervalDialogOpened) {
//                    AlertDialog(onDismissRequest = { isMessageSendingIntervalDialogOpened = false }, title = {
//                        Text(stringResource(R.string.message_settings_screen_sending_interval))
//                    }, text={
//                        TextFieldWithErorr(textField = {
//                            OutlinedTextField(
//                                value = sendingIntervalInMinutes,
//                                onValueChange = { messageSettingsViewModel.onSendingIntervalChange(it) },
//                                isError = sendingIntervalErrorMessage != null
//                                ,
//                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
//                            )
//
//                        }, error = sendingIntervalErrorMessage?.let {
//                            return@let {TextFieldError(error = it)}
//                        })
//                    }, dismissButton = {
//                        Button(onClick = {
//                            isMessageSendingIntervalDialogOpened = false
//                        }) {
//                            Text(stringResource(R.string.all_cancel))
//                        }
//                    }, confirmButton = {
//                        Button(onClick = {
//                            messageSettingsViewModel.submitSendingInterval()
//                            isMessageSendingIntervalDialogOpened = false
//                        }, enabled = sendingIntervalErrorMessage == null) {
//                            Text(stringResource(R.string.all_save))
//                        }
//                    })
//                }
            }
        })


}