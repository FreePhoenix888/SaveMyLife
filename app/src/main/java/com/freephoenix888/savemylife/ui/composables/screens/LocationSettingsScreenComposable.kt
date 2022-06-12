package com.freephoenix888.savemylife.ui.composables.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShareLocation
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.freephoenix888.savemylife.R
import com.freephoenix888.savemylife.ui.composables.SettingSwitchComposable
import com.freephoenix888.savemylife.ui.viewModels.LocationViewModel


@Composable
fun LocationSettingsScreenComposable(
    locationViewModel: LocationViewModel = viewModel()
) {
    val isLocationSharingEnabled by locationViewModel.isLocationSharingEnabled.collectAsState(
        initial = false
    )
    LocationSettingsScreenBodyComposable(
        settingsComposable = {
            SettingSwitchComposable(state = isLocationSharingEnabled, onChangeState = {
                locationViewModel.setIsLocationSharingEnabled(it)
            }, text = {
                Text(stringResource(R.string.location_sharing))
            })
        }
    )
}

@Composable
private fun LocationSettingsScreenBodyComposable(
    settingsComposable: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar {
                Icon(imageVector = Icons.Filled.ShareLocation, contentDescription = "Location")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Location")
            }
        }
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
        }, text = {
            Text("Location sharing")
        })
    })
}