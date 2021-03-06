package com.freephoenix888.savemylife.ui.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.Icon
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import com.freephoenix888.savemylife.R
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.semantics.Role

@Composable
fun SettingSwitchComposable(
    state: Boolean,
    onChangeState: (Boolean) -> Unit,
    iconComposable: @Composable (() -> Unit)? = null,
    textComposable: @Composable () -> Unit,
    switchComposable: @Composable () -> Unit = {
        Switch(
            checked = state,
            onCheckedChange = onChangeState
        )
    }
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.setting_paddings))
            .toggleable(
                value = state,
                role = Role.Switch,
                onValueChange = onChangeState
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (iconComposable != null) {
            iconComposable()
        }
        textComposable()
        switchComposable()
    }
}

@Composable
private fun SettingSwitchComposablePreview() {
    var state by remember { mutableStateOf(false) }
    SettingSwitchComposable(state = state, onChangeState = { state = it }, iconComposable = {
        Icon(imageVector = Icons.Filled.Settings, contentDescription = "Setting")
    }, textComposable = { Text("Setting") })
}