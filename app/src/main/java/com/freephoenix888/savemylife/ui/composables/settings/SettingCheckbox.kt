package com.freephoenix888.savemylife.ui.composables.settings

import android.provider.SyncStateContract.Helpers.update
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import com.alorma.compose.settings.storage.base.SettingValueState
import com.alorma.compose.settings.storage.base.getValue
import com.alorma.compose.settings.storage.base.rememberBooleanSettingState
import com.alorma.compose.settings.storage.base.setValue

@Composable
fun SettingCheckbox(
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
                    role = Role.Checkbox,
                    onValueChange = onCheckedChange
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SettingIcon(icon = icon)
            SettingText(title = title, subtitle = subtitle)
            SettingAction {
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = onCheckedChange
                )
            }
        }
    }
}