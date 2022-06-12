package com.freephoenix888.savemylife.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingTextComposable(
    title: @Composable () -> Unit,
    subtitle: @Composable (() -> Unit)? = null,
) {
    Column(
        verticalArrangement = Arrangement.Center,
    ) {
        title()
        if (subtitle != null) {
            Spacer(modifier = Modifier.height(2.dp))
            SettingsSubtitleComposable(subtitle)
        }
    }
}