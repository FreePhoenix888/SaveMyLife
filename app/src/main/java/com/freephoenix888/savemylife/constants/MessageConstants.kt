package com.freephoenix888.savemylife.constants

import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

object MessageConstants {
    val DEFAULT_SENDING_INTERVAL: Duration =  (30L).toDuration(DurationUnit.MINUTES)
    const val DEFAULT_TEMPLATE: String = "{CONTACT_NAME}, I AM IN DANGER! My location: {LOCATION_URL}"
}