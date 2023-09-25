package com.freephoenix888.savemylife.ui.composables.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Message
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.freephoenix888.savemylife.R
import com.freephoenix888.savemylife.constants.MessageConstants
import com.freephoenix888.savemylife.constants.MessageTemplateVariables
import com.freephoenix888.savemylife.enums.MessageCommand
import com.freephoenix888.savemylife.ui.composables.*
import com.freephoenix888.savemylife.ui.viewModels.MessageSettingsViewModel
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageTemplateSettingsScreen(messageSettingsViewModel: MessageSettingsViewModel, navController: NavController) {

    val messageTemplate by messageSettingsViewModel.messageTemplate.collectAsState()
    val messageTemplateErrorMessage by messageSettingsViewModel.messageTemplateError.collectAsState()
    val isMessageTemplateSaveable by messageSettingsViewModel.isMessageTemplateSaveable.collectAsState(false   )

    var isNotSavedChangedWarningDialogOpened by remember { mutableStateOf(false)}
    if(isNotSavedChangedWarningDialogOpened){
        AlertDialog(onDismissRequest = { isNotSavedChangedWarningDialogOpened = false }, confirmButton = {
            Button(onClick = {
                messageSettingsViewModel.submitMessageTemplate()
                isNotSavedChangedWarningDialogOpened = false
                navController.navigateUp()
            }, content = {
                Text("Save")
            }, enabled = messageTemplateErrorMessage == null)
        }, dismissButton = {
            Button(onClick = {
                isNotSavedChangedWarningDialogOpened = false
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
    BackHandler(enabled = isMessageTemplateSaveable) {
        isNotSavedChangedWarningDialogOpened = true
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Icon(imageVector = Icons.Filled.Message, contentDescription = "Message template")
                        Text("Message Template")
                    }
                },
                navigationIcon = {
                    IconButton(onClick = {
                        if(isMessageTemplateSaveable){
                            isNotSavedChangedWarningDialogOpened = true
                        } else {
                            navController.navigateUp()
                        }
                    }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { messageSettingsViewModel.submitMessageTemplate() }, enabled = isMessageTemplateSaveable) {
                        Icon(imageVector = Icons.Filled.Check, contentDescription = "Save")
                    }
                }
            )
        },
        content = { innerPadding ->
            Column(modifier = Modifier
                .padding(innerPadding)
                .padding(dimensionResource(R.dimen.settings_screen_padding)),
            verticalArrangement = Arrangement.spacedBy(16.dp)) {
                TextFieldWithErorr(textField = {
                    OutlinedTextField(
                        value = messageTemplate,
                        onValueChange = { messageSettingsViewModel.onMessageTemplateChange(it) },
                        isError = messageTemplateErrorMessage != null,
                        leadingIcon = {
                            Icon(imageVector = Icons.Filled.Message, contentDescription = "Message template")
                        }
                    )
                }, error = messageTemplateErrorMessage?.let {
                    return@let { TextFieldError(error = it) }
                })

                Column() {
                    Text("You can use the following \"variables\" in your message:", style = MaterialTheme.typography.headlineSmall)
                    SelectionContainer() {
                        Column() {
                            for (messageTemplateVariable in MessageTemplateVariables.values()) {
                                Button(onClick = {
                                    messageSettingsViewModel.onMessageTemplateChange("${messageTemplate}{${messageTemplateVariable.name}}")
                                }, content = {
                                    Text(messageTemplateVariable.name)
                                })
                            }
                        }
                    }
                }
                Column {
                    Text("Your message will look like this:",  style = MaterialTheme.typography.headlineSmall)
                    MessageCard(
                        message = Message(
                            body = messageTemplate.replace("{${MessageTemplateVariables.CONTACT_NAME}}", MessageConstants.FAKE_CONTACT_NAME)
                                .replace("{${MessageTemplateVariables.LOCATION_URL}}", MessageConstants.FAKE_LOCATION_URL)
                                .replace("{${MessageTemplateVariables.MESSAGE_COMMANDS}}", MessageCommand.values().joinToString { messageCommand -> "/${messageCommand.name.lowercase()}\n" }).trimEnd(),
                            position = MessagePosition.RIGHT,
                            time = LocalTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))
                        )
                    )
                }
            }
        })



}