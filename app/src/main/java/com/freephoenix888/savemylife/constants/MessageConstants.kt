package com.freephoenix888.savemylife.constants

import com.freephoenix888.savemylife.SecondsInterval

object MessageConstants {
    const val DEFAULT_EMERGENCY_MESSAGE_SENDING_SECONDS_INTERVAL: SecondsInterval = 30
    const val DEFAULT_EMERGENCY_MESSAGE_TEMPLATE: String = "{CONTACT_NAME}, I AM IN DANGER! My location: {LOCATION_URL}"
}