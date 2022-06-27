package com.freephoenix888.savemylife.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowRight
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import com.freephoenix888.savemylife.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.freephoenix888.savemylife.domain.models.SettingLink

@Composable
fun SettingLinkComposable(
    settingLink: SettingLink,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .clickable(onClick = settingLink.onClick)
            .padding(dimensionResource(R.dimen.setting_paddings))
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(imageVector = settingLink.icon, contentDescription = settingLink.title)
            Spacer(modifier = Modifier.width(4.dp))
            SettingTextComposable(
                title = {
                    Text(text = settingLink.title)
                }
            )
        }
        Icon(imageVector = Icons.Filled.ArrowRight, contentDescription = null)
    }
}

@Preview(showBackground = true, widthDp = 320)
@Composable
private fun SettingLinkComposablePreview() {
    val settingLink = SettingLink(
        icon = Icons.Filled.Settings,
        title = "Setting",
        onClick = {}
    )
    SettingLinkComposable(settingLink)
}