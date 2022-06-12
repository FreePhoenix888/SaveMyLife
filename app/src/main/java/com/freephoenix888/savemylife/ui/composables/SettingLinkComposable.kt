package com.freephoenix888.savemylife.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.freephoenix888.savemylife.domain.models.SettingLink

@Composable
fun SettingLinkComposable(
    settingLink: SettingLink
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable(onClick = settingLink.onClick)
            .padding(16.dp)
    ) {
        Icon(imageVector = settingLink.icon, contentDescription = settingLink.title)
        Text(settingLink.title)
    }
}

@Preview
@Composable
private fun SettingLinkComposable() {
    SettingLinkComposable()
}