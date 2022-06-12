package com.freephoenix888.savemylife.ui.composables

import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProvideTextStyle
import androidx.compose.runtime.Composable

@Composable
fun SettingsTitleComposable(title: @Composable () -> Unit) {
    ProvideTextStyle(value = MaterialTheme.typography.subtitle1) {
        title()
    }
}
