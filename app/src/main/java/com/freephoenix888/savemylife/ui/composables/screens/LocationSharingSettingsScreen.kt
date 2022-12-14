package com.freephoenix888.savemylife.ui.composables.screens

import android.Manifest
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.BatterySaver
import androidx.compose.material.icons.filled.ShareLocation
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

import androidx.navigation.compose.rememberNavController
import com.freephoenix888.savemylife.BatterySaverModeEnum.BatterySaverMode
import com.freephoenix888.savemylife.R
import com.freephoenix888.savemylife.ui.composables.RequestPermission
import com.freephoenix888.savemylife.ui.composables.settings.SettingCheckbox
import com.freephoenix888.savemylife.ui.composables.settings.SettingLink
import com.freephoenix888.savemylife.ui.viewModels.LocationSharingSettingsViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState


@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun LocationSettingsScreen(
    locationSharingSettingsViewModel: LocationSharingSettingsViewModel = viewModel(),
    navController: NavController = rememberNavController()
) {

    val fineLocationPermissionState = rememberPermissionState(permission = Manifest.permission.ACCESS_FINE_LOCATION)
    if(!fineLocationPermissionState.status.isGranted) {
        RequestPermission(
            permissionState = fineLocationPermissionState,
            permissionHumanReadableName = "Fine location",
            description ="Fine location permission is required to share your fine location with your emergency contacts."
        )
        return
    }

    val coarseLocationPermissionState = rememberPermissionState(permission = Manifest.permission.ACCESS_COARSE_LOCATION)
    if(!coarseLocationPermissionState.status.isGranted) {
        RequestPermission(
            permissionState = fineLocationPermissionState,
            permissionHumanReadableName = "Coarse location",
            description = stringResource(R.string.coarse_location_settings_screen_location_permission_request)
        )
        return
    }

    val backgroundLocationPermissionState =
        rememberPermissionState(permission = Manifest.permission.ACCESS_BACKGROUND_LOCATION)
    if(!backgroundLocationPermissionState.status.isGranted) {
        RequestPermission(
            permissionState = backgroundLocationPermissionState,
            permissionHumanReadableName = "Background location",
            description = stringResource(R.string.background_location_settings_screen_location_permission_request)
        )
        return
    }

    val isLocationSharingEnabled by locationSharingSettingsViewModel.isLocationSharingEnabled.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(
                        imageVector = Icons.Filled.ShareLocation,
                        contentDescription = stringResource(R.string.all_location_sharing)
                    )
                    Text(stringResource(R.string.all_location_sharing))
                }
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
                },
            actions = {
                Switch(checked = isLocationSharingEnabled,
                    onCheckedChange = {
                        locationSharingSettingsViewModel.setIsLocationSharingEnabled(it)            },
                )
            })
        }
    ) { innerPadding: PaddingValues ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(dimensionResource(R.dimen.settings_screen_padding))
                .verticalScroll(rememberScrollState())
                ,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Location sharing", modifier = Modifier.padding(vertical = 8.dp), fontSize = 30.sp, fontWeight = FontWeight.Bold, textAlign = TextAlign.Start)
            Image(painter = painterResource(id = R.drawable.location_on_hand), contentDescription = "Location sharing", modifier = Modifier.clip(
                RoundedCornerShape(16.dp)))
            Text("Location sharing allows you to use {LOCATION_URL} variable in your message template to share your location with your emergency contacts.", modifier = Modifier.padding(vertical = 4.dp), color = Color.White.copy(alpha = 0.7f))

        }
    }
}