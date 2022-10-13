package com.freephoenix888.savemylife.ui.composables.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.freephoenix888.savemylife.R
import com.freephoenix888.savemylife.enums.MessageCommands
import com.freephoenix888.savemylife.ui.composables.RequestPermission
import com.freephoenix888.savemylife.ui.viewModels.MessageSettingsViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
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
                    Row{
                        Icon(imageVector = Icons.Filled.Timer, contentDescription = "Message commands")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Message commands")
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
            actions = {
                Switch(checked = isMessageCommandsEnabled,
                    onCheckedChange = {
                        messageSettingsViewModel.setIsMessageCommandsEnabled(it)            },
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
                    Text("Message commands", modifier = Modifier.padding(vertical = 8.dp), fontSize = 30.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Start)
                    Image(painter = painterResource(R.drawable.message_commands_example), contentDescription = "Message commands example", modifier = Modifier
                        .clip(
                            RoundedCornerShape(24.dp)
                        )
                        .fillMaxWidth()
                        .height(500.dp))
                    ProvideTextStyle(value = TextStyle(color = Color.White.copy(alpha = 0.7f))) {
                        Text("Allows your emergency contacts to send specific commands to get specific information.", modifier = Modifier.padding(vertical = 4.dp))
                        Text("Commands:", style = MaterialTheme.typography.headlineSmall, color = Color.White.copy(alpha = 0.7f))
                        Column() {
                            for (messageCommand in MessageCommands.values()) {
                                Text("/${messageCommand.name.lowercase()}")
                            }
                        }
                    }

                }

        })

}
