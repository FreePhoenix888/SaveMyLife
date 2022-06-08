package com.freephoenix888.savemylife.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShareLocation
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.alorma.compose.settings.storage.base.rememberBooleanSettingState
import com.alorma.compose.settings.ui.SettingsSwitch
import com.freephoenix888.savemylife.ui.viewModels.LocationViewModel

@Composable
fun LocationSettingsScreenComposable(
    locationViewModel: LocationViewModel = viewModel()
) {
    val locationSharingState by locationViewModel.locationSharingState.collectAsState(initial = false)
    LocationSettingsScreenBodyComposable(
        isLocationSharingEnabled = locationSharingState,
        onChangeLocationSharingState = { state: Boolean ->
            locationViewModel.setLocationSharingState(state)
        }
    )
}

@Composable
private fun LocationSettingsScreenBodyComposable(
    isLocationSharingEnabled: Boolean,
    onChangeLocationSharingState: (Boolean) -> Unit
) {
    val isLocationSharingEnabledState = rememberBooleanSettingState(isLocationSharingEnabled)
    Scaffold() { innerPadding: PaddingValues ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SettingsSwitch(title = {
                Text("Location sharing")
            },
            icon = {
                Icon(imageVector = Icons.Filled.ShareLocation, contentDescription = "Location sharing")
            },
                state = isLocationSharingEnabledState,
            onCheckedChange = {
                onChangeLocationSharingState(!it)
            })
        //            Card(
//                border = BorderStroke(
//                    width = 1.dp,
//                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
//                ),
//                modifier = Modifier.padding(16.dp)
//            ) {
//                Surface {
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .toggleable(
//                                value = locationSharingState,
//                                role = Role.Switch,
//                                onValueChange = { onLocationSharingStateChanged(it) }
//                            ),
//                        verticalAlignment = Alignment.CenterVertically,
//                    ) {
//                        Icon(
//                            imageVector = Icons.Filled.ShareLocation,
//                            contentDescription = "Location sharing"
//                        )
//                        Text(text = "Location sharing")
//                        Switch(
//                            checked = locationSharingState,
//                            onCheckedChange = onLocationSharingStateChanged
//                        )
//                    }
//                }
//            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LocationSettingsScreenBodyComposablePreview() {
    var locationSharingState by remember { mutableStateOf(false) }
    LocationSettingsScreenBodyComposable(
        isLocationSharingEnabled = locationSharingState,
        onChangeLocationSharingState = { state: Boolean ->
            locationSharingState = state
        }
    )
}