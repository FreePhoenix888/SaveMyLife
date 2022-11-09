package com.freephoenix888.savemylife.constants

import androidx.core.net.toUri
import com.freephoenix888.savemylife.ui.SaveMyLifeScreenEnum

object DeepLinks {
    val alarmButton = "${Constants.APP_URI}/${SaveMyLifeScreenEnum.AlarmButton.name}".toUri()
}