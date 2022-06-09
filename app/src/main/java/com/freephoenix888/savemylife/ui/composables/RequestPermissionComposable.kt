package com.freephoenix888.savemylife.ui.composables

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestPermissionComposable(
    context: Context,
    permissionState: PermissionState,
    text: String
) {
    Scaffold() {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp)
        ) {
            Text(text)

            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                permissionState.launchPermissionRequest()
            }) {
                Text("Grant permissions")
            }
        }
    }
}
