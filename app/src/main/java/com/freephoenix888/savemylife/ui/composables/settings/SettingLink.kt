package com.freephoenix888.savemylife.ui.composables.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SettingLink(
    modifier: Modifier = Modifier,
    icon: (@Composable () -> Unit)? = null,
    title: @Composable () -> Unit,
    subtitle: (@Composable () -> Unit)? = null,
    action: (@Composable () -> Unit)? = null,
    onClick: () -> Unit,
) {
    Surface {
        Row(
            modifier = modifier.fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .clickable(onClick = onClick),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                SettingIcon(icon = icon)
                SettingText(title = title, subtitle = subtitle)
            }
            if (action != null) {
                Divider(
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .height(56.dp)
                        .width(1.dp),
                )
                SettingAction {
                    action()
                }
            }
        }
    }
}

@Preview
@Composable
internal fun SettingsMenuLinkPreview() {
    MaterialTheme {
        SettingLink(
            icon = { Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear") },
            title = { Text(text = "Hello") },
            subtitle = { Text(text = "This is a longer text") },
        ) {

        }
    }
}

@Preview
@Composable
internal fun SettingsMenuLinkActionPreview() {
    var rememberCheckBoxState by remember { mutableStateOf(true) }
    MaterialTheme {
        SettingLink(
            icon = { Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear") },
            title = { Text(text = "Hello") },
            subtitle = { Text(text = "This is a longer text") },
            action = {
                Checkbox(checked = rememberCheckBoxState, onCheckedChange = { newState ->
                    rememberCheckBoxState = newState
                })
            },
        ) {

        }
    }
}