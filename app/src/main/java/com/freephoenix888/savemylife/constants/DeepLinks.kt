package com.freephoenix888.savemylife.constants

import androidx.core.net.toUri
import com.freephoenix888.savemylife.ui.SaveMyLifeScreenEnum

object DeepLinks {
    val dangerButton = "${Constants.APP_URI}/${SaveMyLifeScreenEnum.DangerButton.name}".toUri()
}