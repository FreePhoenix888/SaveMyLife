package com.freephoenix888.savemylife.ui.composables

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.freephoenix888.savemylife.R
import com.freephoenix888.savemylife.constants.Constants
import com.google.accompanist.permissions.*

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun RequestPermission(
    permissionState: PermissionState,
    permissionRequestHandler: (context: Context, permissionState: PermissionState,  isFirstRequest: Boolean) -> Unit = { context: Context, permissionState: PermissionState, isFirstRequest: Boolean ->
        if(isFirstRequest) {
            permissionState.launchPermissionRequest()
        } else {
            val intent = Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:${Constants.APP_PACKAGE_NAME}")
            )
            context.startActivity(intent)
        }
    },
    permissionHumanReadableName: String,
    description: String,
) {
        val context = LocalContext.current

        var isFirstRequest by remember {
            mutableStateOf(true)
        }
        Scaffold {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp)
                    .padding(it)
            ) {
                Spacer(Modifier.weight(1f))

//                Text(buildAnnotatedString {
//                    /*withStyle(SpanStyle(fontWeight = FontWeight.Bold)){
//                        append("Provide")
//                    }
//                    withStyle(ParagraphStyle()) {
//                        append(permissionHumanReadableName)
//                    }
//                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)){
//                        append("permission")
//                    }*/
//                }, textAlign = TextAlign.Center)
                Text(style = MaterialTheme.typography.headlineMedium, text = buildAnnotatedString {
                        append("Provide")
                    withStyle(ParagraphStyle()) {
                        append(permissionHumanReadableName)
                    }
                        append("permission")
                }, textAlign = TextAlign.Center)

                Spacer(modifier = Modifier.height(24.dp))


                Text(description, textAlign = TextAlign.Center)

                Spacer(modifier = Modifier.height(24.dp))

                Button(onClick = {
                    permissionRequestHandler(context, permissionState,isFirstRequest)
                    isFirstRequest = !isFirstRequest
                }) {
                    Text(stringResource(R.string.all_grant_permission))
                }
                Spacer(Modifier.weight(1f))
                TextButton(onClick = {
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/FreePhoenix888/SaveMyLife"))
                    startActivity(context, browserIntent, null)
                }) {
                    Text("If you do not trust this application you can read the source code", textAlign = TextAlign.Center)
                }
            }
        }


}
