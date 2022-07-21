package com.freephoenix888.savemylife.ui.composables.screens

import android.Manifest
import android.os.Build
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Timer
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.alorma.compose.settings.ui.SettingsMenuLink
import com.freephoenix888.savemylife.R
import com.freephoenix888.savemylife.ui.MessageSettingsFormEvent
import com.freephoenix888.savemylife.ui.composables.RequestPermissionComposable
import com.freephoenix888.savemylife.ui.composables.TextFieldError
import com.freephoenix888.savemylife.ui.viewModels.MessageSettingsViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MessageSettingsScreenComposable(
    messageSettingsViewModel: MessageSettingsViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    if(Build.VERSION.SDK_INT >= 31) {
        val scheduleExactPermission = rememberPermissionState(permission = Manifest.permission.SCHEDULE_EXACT_ALARM)
        if(scheduleExactPermission.status != PermissionStatus.Granted){
            RequestPermissionComposable(
                permissionState = scheduleExactPermission,
                text = stringResource(R.string.message_settings_screen_schedule_exact_alarm_permission_request)
            )
            return
        }
    }

    val context = LocalContext.current
    val messageSettingsFormState by messageSettingsViewModel.state.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Icon(
                    imageVector = Icons.Filled.Message,
                    contentDescription = stringResource(R.string.all_message)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(stringResource(R.string.all_message))
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
                SettingsMenuLink(icon = {
                    Icon(
                        imageVector = Icons.Filled.Message,
                        contentDescription = stringResource(R.string.message_settings_screen_message_template)
                    )
                },title = {
                    Text("Message template")
                }, onClick = {
                    isMessageTemplateDialogOpened = true
                })
                if(isMessageTemplateDialogOpened) {
                    AlertDialog(onDismissRequest = { isMessageTemplateDialogOpened = false }, title = {
                        Text(stringResource(R.string.message_settings_screen_message_template))
                    }, text={
                        OutlinedTextField(
                            value = messageSettingsFormState.template,
                            onValueChange = { messageSettingsViewModel.onEvent(
                                MessageSettingsFormEvent.TemplateChanged(it)) },
                            isError = messageSettingsFormState.templateErrorMessage != null
                        )
                        messageSettingsFormState.templateErrorMessage?.let {
                            TextFieldError(error = it)
                        }
                    }, dismissButton = {
                        Button(onClick = {
                            isMessageTemplateDialogOpened = false
                        }) {
                            Text(stringResource(R.string.all_cancel))
                        }
                    }, confirmButton = {
                        Button(onClick = { isMessageTemplateDialogOpened = false}) {
                            Text(stringResource(R.string.all_save))
                        }
                    })
                }

                var isMessageSendingIntervalDialogOpened by remember { mutableStateOf(false) }
                SettingsMenuLink(icon = {
                    Icon(
                        imageVector = Icons.Filled.Timer,
                        contentDescription = stringResource(R.string.message_settings_screen_sending_interval)
                    )
                },title = {
                    Text(stringResource(R.string.message_settings_screen_sending_interval))
                }, onClick = {
                    isMessageSendingIntervalDialogOpened = true
                })
                if(isMessageSendingIntervalDialogOpened) {
                    AlertDialog(onDismissRequest = { isMessageSendingIntervalDialogOpened = false }, title = {
                        Text(stringResource(R.string.message_settings_screen_sending_interval))
                    }, text={
                        OutlinedTextField(
                            value = messageSettingsFormState.sendingIntervalInMinutes,
                            onValueChange = { messageSettingsViewModel.onEvent(
                                MessageSettingsFormEvent.SendingIntervalInMinutesChanged(it)) },
                            isError = messageSettingsFormState.sendingIntervalInMinutesErrorMessage != null
                        )
                        messageSettingsFormState.sendingIntervalInMinutesErrorMessage?.let {
                            TextFieldError(error = it)
                        }
                    }, dismissButton = {
                        Button(onClick = {
                            isMessageSendingIntervalDialogOpened = false
                        }) {
                            Text(stringResource(R.string.all_cancel))
                        }
                    }, confirmButton = {
                        Button(onClick = { isMessageSendingIntervalDialogOpened = false}) {
                            Text(stringResource(R.string.all_save))
                        }
                    })
                }
            }
        })


}