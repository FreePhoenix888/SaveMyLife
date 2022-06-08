package com.freephoenix888.savemylife.domain.models

import androidx.compose.ui.graphics.vector.ImageVector
import com.freephoenix888.savemylife.ui.SaveMyLifeScreenEnum

data class Setting(
    val iconVector: ImageVector,
    val title: String,
    val screen: SaveMyLifeScreenEnum
)
