package com.freephoenix888.savemylife.ui.composables.screens

import android.Manifest
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.freephoenix888.savemylife.R
import com.freephoenix888.savemylife.constants.MessageConstants
import com.freephoenix888.savemylife.enums.MessageCommand
import com.freephoenix888.savemylife.ui.composables.*
import com.freephoenix888.savemylife.ui.viewModels.MessageSettingsViewModel
import com.google.accompanist.permissions.*
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MessageCommandsSettingsScreen(
    messageSettingsViewModel: MessageSettingsViewModel,
    navController: NavHostController
) {
        val receiveSmsPermissionsState = rememberPermissionState(permission = Manifest.permission.RECEIVE_SMS)
    if (!receiveSmsPermissionsState.status.isGranted) {
        RequestPermission(
            permissionState = receiveSmsPermissionsState,
            permissionHumanReadableName = "Receive sms",
            description = "Receive sms permission is used to use find message commands from your messages."
        )
        return
    }

    val sendSmsPermissionsState = rememberPermissionState(permission = Manifest.permission.SEND_SMS)
    if (!sendSmsPermissionsState.status.isGranted) {
        RequestPermission(
            permissionState = sendSmsPermissionsState,
            permissionHumanReadableName = "Send sms",
            description = "Send sms permission is used to respond to message commands of your emergency contacts."
        )
        return
    }

    val isMessageCommandsEnabled by messageSettingsViewModel.isMessageCommandsEnabled.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Timer,
                            contentDescription = "Message commands"
                        )
                        Text("Message commands")
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    Switch(
                        checked = isMessageCommandsEnabled,
                        onCheckedChange = {
                            messageSettingsViewModel.setIsMessageCommandsEnabled(it)
                        },
                    )

                })
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
                Text(
                    "Message commands",
                    modifier = Modifier.padding(vertical = 8.dp),
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                )
                MessageCard(
                    message = Message(
                        body = MessageConstants.FAKE_MESSAGE,
                        position = MessagePosition.LEFT,
                        time = LocalTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))
                    )
                )
                MessageCard(
                    message = Message(
                        body = "/${MessageCommand.LOCATION.name.lowercase()}",
                        position = MessagePosition.RIGHT,
                        time = LocalTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))
                    )
                )
                MessageCard(
                    message = Message(
                        body = MessageConstants.FAKE_LOCATION_URL,
                        position = MessagePosition.LEFT,
                        time = LocalTime.now().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT))
                    )
                )
                ProvideTextStyle(value = TextStyle(color = Color.White.copy(alpha = 0.7f))) {
                    Text(
                        "Allows your emergency contacts to send specific commands to get specific information.",
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                    Text(
                        "Commands:",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                    Column() {
                        MessageCommand.values().forEach { messageCommand ->
                            Text(buildAnnotatedString {
                                withStyle(style = SpanStyle(fontWeight = FontWeight.ExtraBold)) {
                                    append("/${messageCommand.name.lowercase()}")
                                }
                                append(" - ${messageCommand.description}")
                            })
                        }
                    }
                }

            }

        })

}
