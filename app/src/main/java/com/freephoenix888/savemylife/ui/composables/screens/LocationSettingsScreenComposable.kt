package com.freephoenix888.savemylife.ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShareLocation
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.freephoenix888.savemylife.ui.viewModels.LocationViewModel

@Composable
fun LocationSettingsScreenComposable(
    locationViewModel: LocationViewModel = viewModel()
) {
    val locationSharingState by locationViewModel.locationSharingState.collectAsState(initial = false)
    LocationSettingsScreenBodyComposable(
        locationSharingState = locationSharingState,
        onLocationSharingStateChanged = { state: Boolean ->
            locationViewModel.setLocationSharingState(state)
        }
    )
}

@Composable
private fun LocationSettingsScreenBodyComposable(
    locationSharingState: Boolean,
    onLocationSharingStateChanged: (Boolean) -> Unit
) {
    Scaffold() { innerPadding: PaddingValues ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
                ),
                modifier = Modifier.padding(16.dp)
            ) {
                Surface {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .toggleable(
                                value = locationSharingState,
                                role = Role.Switch,
                                onValueChange = { onLocationSharingStateChanged(it) }
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ShareLocation,
                            contentDescription = "Location sharing"
                        )
                        Text(text = "Location sharing")
                        Switch(
                            checked = locationSharingState,
                            onCheckedChange = onLocationSharingStateChanged
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun LocationSettingsScreenBodyComposablePreview() {
    var locationSharingState by remember { mutableStateOf(false) }
    LocationSettingsScreenBodyComposable(
        locationSharingState = locationSharingState,
        onLocationSharingStateChanged = { state: Boolean ->
            locationSharingState = state
        }
    )
}