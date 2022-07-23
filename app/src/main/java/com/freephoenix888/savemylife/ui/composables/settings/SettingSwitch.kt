package com.freephoenix888.savemylife.ui.composables.settings

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SettingSwitch(
    modifier: Modifier = Modifier,
    icon: @Composable (() -> Unit)? = null,
    title: @Composable () -> Unit,
    subtitle: @Composable (() -> Unit)? = null,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Surface {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .toggleable(
                    value = isChecked,
                    role = Role.Switch,
                    onValueChange = onCheckedChange
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SettingIcon(icon = icon)
            SettingText(title = title, subtitle = subtitle)
            SettingAction {
                Switch(
                    checked = isChecked,
                    onCheckedChange = onCheckedChange
                )
            }
        }
    }
}

@Preview
@Composable
internal fun SettingsSwitchPreview() {
    MaterialTheme {
        var isChecked by remember { mutableStateOf(false)}
        SettingSwitch(
            isChecked = isChecked,
            icon = { Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear") },
            title = { Text(text = "Hello") },
            subtitle = { Text(text = "This is a longer text") },
            onCheckedChange = { isChecked = it }
        )
    }
}