package com.freephoenix888.savemylife.ui.composables.screens

import android.Manifest
import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Timer
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.freephoenix888.savemylife.R
import com.freephoenix888.savemylife.domain.models.MessageSettingsFormState
import com.freephoenix888.savemylife.ui.MessageSettingsFormEvent
import com.freephoenix888.savemylife.ui.composables.RequestPermissionComposable
import com.freephoenix888.savemylife.ui.composables.TextFieldWithError
import com.freephoenix888.savemylife.ui.viewModels.MessageSettingsViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MessageSettingsScreenComposable(
    messageSettingsViewModel: MessageSettingsViewModel = viewModel()
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
    val messageFormState by messageSettingsViewModel.state.collectAsState()
    MessageSettingsScreenBodyComposable(
        messageSettingsFormState = messageFormState,
        onTemplateChange = {
            messageSettingsViewModel.onEvent(MessageSettingsFormEvent.TemplateChanged(it))
        },
        onMessageTemplateInfoButtonClick = { /*TODO*/ },
        onSendingIntervalInSecondsChange = {
            messageSettingsViewModel.onEvent(MessageSettingsFormEvent.sendingIntervalInSecondsChanged(it))
        },
        onSubmit = {
            messageSettingsViewModel.onEvent(MessageSettingsFormEvent.Submit)
        },
        onLaunchedEffect = {
            messageSettingsViewModel.validationEvents.collect {
                when(it) {
                    is MessageSettingsViewModel.ValidationEvent.Success -> {
                        Toast.makeText(context,
                        "Setting are saved successfully",
                        Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    )
}

@Composable
private fun MessageSettingsScreenBodyComposable(
    messageSettingsFormState: MessageSettingsFormState,
    onTemplateChange: (String) -> Unit,
    onMessageTemplateInfoButtonClick: () -> Unit,
    onSendingIntervalInSecondsChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onLaunchedEffect: suspend () -> Unit
) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Icon(
                    imageVector = Icons.Filled.Message,
                    contentDescription = stringResource(R.string.all_message)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Message")
            }, actions = {
                IconButton(onClick = {
                    onSubmit()
                }) {
                    Icon(
                        imageVector = Icons.Filled.Save,
                        contentDescription = stringResource(R.string.all_save)
                    )
                }
            })
        },
        content = { innerPadding: PaddingValues ->
            LaunchedEffect(key1 = context, block = {
                onLaunchedEffect()
            })
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {

                    Column(modifier = Modifier.weight(9f)) {
                        TextFieldWithError(textFieldComposable = {
                            OutlinedTextField(
                                value = messageSettingsFormState.template,
                                onValueChange = { onTemplateChange(it) },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Filled.Message,
                                        contentDescription = "Message"
                                    )
                                },
                                label = {
                                    Text("Message template")
                                },
                                isError = messageSettingsFormState.templateErrorMessage != null
                            )

                        }, error = messageSettingsFormState.templateErrorMessage)

                    }
                    IconButton(
                        onClick = onMessageTemplateInfoButtonClick,
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = "String template information"
                        )
                    }
                }

                Divider(modifier = Modifier.padding(vertical = 16.dp))

                TextFieldWithError(textFieldComposable = {
                    OutlinedTextField(
                        value = messageSettingsFormState.sendingIntervalInSeconds,
                        onValueChange = {
                            onSendingIntervalInSecondsChange(it)
                        },
                        label = {
                            Text("Sending interval in seconds")
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Timer,
                                contentDescription = "Sending interval"
                            )
                        },
                        isError = messageSettingsFormState.sendingIntervalInSecondsErrorMessage != null,
                        modifier = Modifier.fillMaxWidth()
                    )
                }, error = messageSettingsFormState.sendingIntervalInSecondsErrorMessage)
            }
        })
}

@Preview(showBackground = true)
@Composable
private fun MessageSettingsScreenBodyComposablePreview() {
    val context = LocalContext.current
    var messageSettingsFormState by remember { mutableStateOf(MessageSettingsFormState()) }
    MessageSettingsScreenBodyComposable(
        messageSettingsFormState = messageSettingsFormState,
        onTemplateChange = {
            messageSettingsFormState = messageSettingsFormState.copy(template = it)
        },
        onMessageTemplateInfoButtonClick = {
        },
        onSendingIntervalInSecondsChange = {
            messageSettingsFormState = messageSettingsFormState.copy(sendingIntervalInSeconds = it)
        },
        onSubmit = {},
        onLaunchedEffect = {}
    )
}