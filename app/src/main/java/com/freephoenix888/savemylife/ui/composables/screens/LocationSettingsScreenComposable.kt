package com.freephoenix888.savemylife.ui.composables.screens

import android.Manifest
import android.os.Build
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShareLocation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.alorma.compose.settings.storage.base.rememberBooleanSettingState
import com.alorma.compose.settings.ui.SettingsSwitch
import com.freephoenix888.savemylife.R
import com.freephoenix888.savemylife.ui.LocationSettingsFormEvent
import com.freephoenix888.savemylife.ui.composables.RequestPermissionComposable
import com.freephoenix888.savemylife.ui.viewModels.LocationSettingsViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationSettingsScreenComposable(
    locationSettingsViewModel: LocationSettingsViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val coarseLocationPermissionState =
        rememberPermissionState(permission = Manifest.permission.ACCESS_COARSE_LOCATION)
    if (coarseLocationPermissionState.status != PermissionStatus.Granted) {
        RequestPermissionComposable(
            permissionState = coarseLocationPermissionState,
            text = stringResource(R.string.coarse_location_settings_screen_location_permission_request)
        )
        return
    }

    val fineLocationPermissionState =
        rememberPermissionState(permission = Manifest.permission.ACCESS_FINE_LOCATION)
    if (fineLocationPermissionState.status != PermissionStatus.Granted) {
        RequestPermissionComposable(
            permissionState = fineLocationPermissionState,
            text = stringResource(R.string.fine_location_settings_screen_location_permission_request)
        )
        return
    }

    if (Build.VERSION.SDK_INT >= 29) {
        val backgroundLocationPermissionState =
            rememberPermissionState(permission = Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        if (backgroundLocationPermissionState.status != PermissionStatus.Granted) {
            RequestPermissionComposable(
                permissionState = backgroundLocationPermissionState,
                text = stringResource(R.string.background_location_settings_screen_location_permission_request)
            )
            return
        }
    }

    val state by locationSettingsViewModel.state.collectAsState()
    val isLocationSharingEnabled = rememberBooleanSettingState()
    LaunchedEffect(key1 = state.isLocationSharingEnabled, block = {
        isLocationSharingEnabled.value = state.isLocationSharingEnabled
    })
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Icon(
                    imageVector = Icons.Filled.ShareLocation,
                    contentDescription = stringResource(R.string.all_location)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(stringResource(R.string.all_location))
            },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }, content = {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    })
                })
        }
    ) { innerPadding: PaddingValues ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SettingsSwitch(state = isLocationSharingEnabled, onCheckedChange = {
                locationSettingsViewModel.onEvent(
                    LocationSettingsFormEvent.IsLocationSharingEnabledChanged(
                        it
                    )
                )
            }, icon = {
                      Icon(imageVector = Icons.Filled.ShareLocation, contentDescription = stringResource(R.string.location_settings_screen_location_sharing))
            }, title = {
                Text(stringResource(R.string.location_settings_screen_location_sharing))
            })
        }
    }

}