package com.freephoenix888.savemylife.ui.composables.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Timer
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.freephoenix888.savemylife.SecondsInterval
import com.freephoenix888.savemylife.constants.MessageConstants
import com.freephoenix888.savemylife.ui.viewModels.MessageViewModel

@Composable
fun MessageSettingsScreenComposable(
    emergencyMessageViewModel: MessageViewModel = viewModel()
) {
    val emergencyMessageTemplate by emergencyMessageViewModel.messageTemplate.collectAsState(initial = MessageConstants.DEFAULT_EMERGENCY_MESSAGE_TEMPLATE)
    val emergencyMessageSendingInterval by emergencyMessageViewModel.sendingInterval.collectAsState(
        initial = MessageConstants.DEFAULT_EMERGENCY_MESSAGE_SENDING_SECONDS_INTERVAL
    )
    MessageSettingsScreenBodyComposable(
        emergencyMessageTemplate = emergencyMessageTemplate,
        onMessageTemplateChange = { newMessageTemplate: String ->
            emergencyMessageViewModel.setMessageTemplate(newMessageTemplate)
        },
        onMessageTemplateInfoButtonClick = { /*TODO*/ },
        messageSendingInterval = emergencyMessageSendingInterval,
        onMessageSendingIntervalChange = {
            emergencyMessageViewModel.setSendingInterval(it)
        },
        emergencyMessageExample = emergencyMessageViewModel.emergencyMessageExample
    )
}

@Composable
private fun MessageSettingsScreenBodyComposable(
    emergencyMessageTemplate: String,
    onMessageTemplateChange: (String) -> Unit,
    onMessageTemplateInfoButtonClick: () -> Unit,
    messageSendingInterval: SecondsInterval,
    onMessageSendingIntervalChange: (SecondsInterval) -> Unit,
    emergencyMessageExample: String?
) {
//    var a by remember { mutableStateOf("A")}
//    OutlinedTextField(value = a, onValueChange = { a = it})
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Icon(imageVector = Icons.Filled.Message, contentDescription = "Message")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Message")
            })
        }
    ) { innerPadding: PaddingValues ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = emergencyMessageTemplate,
                    onValueChange = onMessageTemplateChange,
                    leadingIcon = {
                        Icon(imageVector = Icons.Filled.Message, contentDescription = "Message")
                    },
                    modifier = Modifier.weight(9f),
                    label = {
                        Text("Message template")
                    }
                )
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
            if (emergencyMessageExample != null) {
                Text("Your message will look like this:")
                Text(emergencyMessageExample)
            }
            Divider(modifier = Modifier.padding(vertical = 32.dp))
            OutlinedTextField(
                value = messageSendingInterval.toString(),
                onValueChange = {
                    onMessageSendingIntervalChange(it.toLongOrNull() ?: 0)
                },
                label = {
                    Text("Sending interval in seconds")
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                leadingIcon = {
                    Icon(imageVector = Icons.Filled.Timer, contentDescription = "Sending interval")
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MessageSettingsScreenBodyComposablePreview() {
    val context = LocalContext.current
    var messageTemplate by remember { mutableStateOf("") }
    var messageSendingInterval by remember { mutableStateOf(MessageConstants.DEFAULT_EMERGENCY_MESSAGE_SENDING_SECONDS_INTERVAL) }
    MessageSettingsScreenBodyComposable(
        emergencyMessageTemplate = messageTemplate,
        onMessageTemplateChange = { messageTemplate = it },
        onMessageTemplateInfoButtonClick = {
            Toast.makeText(
                context,
                "String template information buttin is clicked",
                Toast.LENGTH_SHORT
            ).show()
        },
        messageSendingInterval = messageSendingInterval,
        onMessageSendingIntervalChange = { messageSendingInterval = it },
        emergencyMessageExample = "Example"
    )
}