package com.freephoenix888.savemylife.ui.composables.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.freephoenix888.savemylife.R
import com.freephoenix888.savemylife.constants.MessageTemplateVariables
import com.freephoenix888.savemylife.ui.composables.TextFieldError
import com.freephoenix888.savemylife.ui.composables.TextFieldWithErorr
import com.freephoenix888.savemylife.ui.viewModels.MessageSettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageTemplateSettingsScreen(messageSettingsViewModel: MessageSettingsViewModel, navController: NavHostController) {

    val messageTemplate by messageSettingsViewModel.messageTemplate.collectAsState()
    val messageTemplateErrorMessage by messageSettingsViewModel.messageTemplateErrorMessage.collectAsState()
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                        Icon(imageVector = Icons.Filled.Message, contentDescription = "Message template")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Message Template")
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
                        Icon(imageVector = Icons.Filled.Save, contentDescription = "Save")
                    }
                }
            )
        },
        content = { innerPadding ->
            Column(modifier = Modifier
                .padding(innerPadding)
                .padding(dimensionResource(R.dimen.settings_screen_padding))) {
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

                Spacer(modifier = Modifier.height(18.dp))

                Text("You can use the following \"variables\" in your message:", style = MaterialTheme.typography.headlineSmall)
                SelectionContainer() {
                    Column() {
                        for (messageTemplateVariable in MessageTemplateVariables.values()) {
                            Button(onClick = {
                                messageSettingsViewModel.onMessageTemplateChange("${messageTemplate}{${messageTemplateVariable.name}}")
                            }, content = {
                                Text(messageTemplateVariable.name)
                            })
//                            Text(messageTemplateVariable.name, fontFamily = FontFamily.Monospace)
                        }
                    }
                }
                Text("Syntax: {VARIABLE_NAME}", style = MaterialTheme.typography.bodyMedium)
                Text("Example: {CONTACT_NAME}", style = MaterialTheme.typography.bodyMedium)
            }

        })



}