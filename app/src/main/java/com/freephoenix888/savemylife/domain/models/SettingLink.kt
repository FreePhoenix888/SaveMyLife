package com.freephoenix888.savemylife.domain.models

import androidx.compose.ui.graphics.vector.ImageVector

data class SettingLink(
    val icon: ImageVector,
    val title: String,
    val onClick: () -> Unit,
)
