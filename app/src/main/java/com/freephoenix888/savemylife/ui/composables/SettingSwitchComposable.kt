package com.freephoenix888.savemylife.ui.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.Icon
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShareLocation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role

@Composable
fun SettingSwitchComposable(
    state: Boolean,
    onChangeState: (Boolean) -> Unit,
    icon: @Composable (() -> Unit)? = null,
    text: @Composable () -> Unit,
    switch: @Composable () -> Unit = { Switch(checked = state, onCheckedChange = onChangeState)}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .toggleable(
                value = state,
                role = Role.Switch,
                onValueChange = onChangeState
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (icon != null) {
            Icon(
                imageVector = Icons.Filled.ShareLocation,
                contentDescription = "Location sharing"
            )
        }
        Text(text = "Location sharing")
        Switch(
            checked = state,
            onCheckedChange = onChangeState
        )
    }
}
