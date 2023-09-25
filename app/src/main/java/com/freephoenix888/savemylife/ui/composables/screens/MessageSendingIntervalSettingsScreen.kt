package com.freephoenix888.savemylife.ui.composables.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import com.freephoenix888.savemylife.R
import com.freephoenix888.savemylife.constants.MessageConstants
import com.freephoenix888.savemylife.ui.composables.*
import com.freephoenix888.savemylife.ui.viewModels.MessageSettingsViewModel
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import kotlin.time.DurationUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageSendingIntervalSettingsScreen(messageSettingsViewModel: MessageSettingsViewModel, navController: NavController) {
    val messageSendingInterval by messageSettingsViewModel.sendingInterval.collectAsState()
    val messageSendingIntervalUiState by messageSettingsViewModel.sendingIntervalUiState.collectAsState()
    val messageSendingIntervalErrorMessage by messageSettingsViewModel.sendingIntervalError.collectAsState()
    val isMessageSendingIntervalSaveable by messageSettingsViewModel.isSendingIntervalSaveable.collectAsState(false)

    var isNotSavedChangesWarningDialogOpened by remember { mutableStateOf(false)}
    if(isNotSavedChangesWarningDialogOpened){
        AlertDialog(onDismissRequest = { isNotSavedChangesWarningDialogOpened = false }, confirmButton = {
            Button(onClick = {
                messageSettingsViewModel.submitSendingInterval()
                isNotSavedChangesWarningDialogOpened = false
                navController.navigateUp()
            }, content = {
                Text("Save")
            }, enabled = isMessageSendingIntervalSaveable)
        }, dismissButton = {
            Button(onClick = {
                isNotSavedChangesWarningDialogOpened = false
                navController.navigateUp()
            }, content = {
                Text("Discard")
            })
        }, title = {
            Text("Unsaved changes")
        }, text = {
            Text("You have unsaved changes")
        })

    }

    BackHandler(enabled = isMessageSendingIntervalSaveable) {
        isNotSavedChangesWarningDialogOpened = true
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                            Icon(imageVector = Icons.Filled.Timer, contentDescription = "Message sending interval")
                            Text("Message sending interval")
                        }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        if(isMessageSendingIntervalSaveable){
                            isNotSavedChangesWarningDialogOpened = true
                        } else {
                            navController.navigateUp()
                        }
                    }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                     IconButton(onClick = {
                        messageSettingsViewModel.submitSendingInterval()
                    }, content = {
                        Icon(imageVector = Icons.Filled.Check, contentDescription = "Save")
                    }, enabled = isMessageSendingIntervalSaveable)

                }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(dimensionResource(R.dimen.settings_screen_padding))
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text("Message sending interval", style = MaterialTheme.typography.displaySmall)
                MessageCard(message = Message(
                    body = MessageConstants.FAKE_MESSAGE,
                    position = MessagePosition.RIGHT,
                    time = LocalTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))
                ))
                MessageCard(message = Message(
                    body = MessageConstants.FAKE_MESSAGE,
                    position = MessagePosition.RIGHT,
                    time = LocalTime.now().plusMinutes(messageSendingIntervalUiState.toLongOrNull() ?: messageSendingInterval.toLong(DurationUnit.MINUTES)).format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))
                ))
                ProvideTextStyle(value = TextStyle(color = Color.White.copy(alpha = 0.7f))) {
                    Text("Specifies an interval in minutes between messages sent to the emergency contacts.", modifier = Modifier.padding(vertical = 4.dp))
                    TextFieldWithErorr(textField = {
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = messageSendingIntervalUiState,
                            onValueChange = { messageSettingsViewModel.onSendingIntervalChange(it) },
                            isError = messageSendingIntervalErrorMessage != null,
                            leadingIcon = {
                                Icon(imageVector = Icons.Filled.Timer, contentDescription = "Message sending interval")
                            },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                    }, error = messageSendingIntervalErrorMessage?.let {
                        return@let { TextFieldError(error = it) }
                    })

                    Spacer(modifier = Modifier.height(18.dp))
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)) {
                        Icon(imageVector = Icons.Filled.Warning, contentDescription = "Warning")
                        Text("Be careful, low message sending interval will reduce battery life.", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        })
}