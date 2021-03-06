package com.freephoenix888.savemylife.ui.composables.screens

import android.Manifest
import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.ShareLocation
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.freephoenix888.savemylife.R
import com.freephoenix888.savemylife.ui.LocationSettingsFormEvent
import com.freephoenix888.savemylife.ui.composables.RequestPermissionComposable
import com.freephoenix888.savemylife.ui.composables.SettingSwitchComposable
import com.freephoenix888.savemylife.ui.viewModels.LocationSettingsViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationSettingsScreenComposable(
    locationSettingsViewModel: LocationSettingsViewModel = viewModel()
) {
    val coarseLocationPermissionState =
        rememberPermissionState(permission = Manifest.permission.ACCESS_COARSE_LOCATION)
    if(coarseLocationPermissionState.status != PermissionStatus.Granted) {
        RequestPermissionComposable(
            permissionState = coarseLocationPermissionState,
            text = stringResource(R.string.coarse_location_settings_screen_location_permission_request)
        )
        return
    }

    val fineLocationPermissionState =
        rememberPermissionState(permission = Manifest.permission.ACCESS_FINE_LOCATION)
    if(fineLocationPermissionState.status != PermissionStatus.Granted) {
        RequestPermissionComposable(
            permissionState = fineLocationPermissionState,
            text = stringResource(R.string.fine_location_settings_screen_location_permission_request)
        )
        return
    }

    if(Build.VERSION.SDK_INT >= 29) {
        val backgroundLocationPermissionState =
            rememberPermissionState(permission = Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        if(backgroundLocationPermissionState.status != PermissionStatus.Granted) {
            RequestPermissionComposable(
                permissionState = backgroundLocationPermissionState,
                text = stringResource(R.string.background_location_settings_screen_location_permission_request)
            )
            return
        }
    }

    val state by locationSettingsViewModel.state.collectAsState()
    val context = LocalContext.current
    LocationSettingsScreenBodyComposable(
        settingsComposable = {
            SettingSwitchComposable(state = state.isLocationSharingEnabled, onChangeState = {
                locationSettingsViewModel.onEvent(LocationSettingsFormEvent.IsLocationSharingEnabledChanged(it))
            }, textComposable = {
                Text(stringResource(R.string.location_settings_screen_location_sharing))
            })
        },
        onSubmit = {
            locationSettingsViewModel.onEvent(LocationSettingsFormEvent.Submit)
        },
        onLaunchedEffect = {
            locationSettingsViewModel.validationEvents.collect {
                when(it) {
                    is LocationSettingsViewModel.ValidationEvent.Success -> {
                        Toast.makeText(context, "The settings are successfully saved", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    )
}

@Composable
private fun LocationSettingsScreenBodyComposable(
    settingsComposable: @Composable () -> Unit,
    onSubmit: () -> Unit,
    onLaunchedEffect: suspend () -> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = context, block = {
        onLaunchedEffect()
    })
    Scaffold(
        topBar = {
            TopAppBar(actions = {
                                IconButton(onClick = {
                                    onSubmit()
                                }) {
                                    Icon(imageVector = Icons.Filled.Save, contentDescription = "Save")
                                }
            }, title = {
                Icon(imageVector = Icons.Filled.ShareLocation, contentDescription = stringResource(R.string.all_location))
                Spacer(modifier = Modifier.width(8.dp))
                Text(stringResource(R.string.all_location))

            })         }
    ) { innerPadding: PaddingValues ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            settingsComposable()
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun LocationSettingsScreenBodyComposablePreview() {
    var locationSharingState by remember { mutableStateOf(false) }
    LocationSettingsScreenBodyComposable(settingsComposable = {
        SettingSwitchComposable(state = locationSharingState, onChangeState = {
            locationSharingState = it
        }, textComposable = {
            Text("Location sharing")
        })
    },
    onSubmit = {},
    onLaunchedEffect = {})
}