package com.freephoenix888.savemylife.constants

import com.freephoenix888.savemylife.enums.messageCommandsAndDescriptionsString
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

enum class MessageTemplateVariables {
    CONTACT_NAME,
    LOCATION_URL,
    MESSAGE_COMMANDS
}

object MessageConstants {
    val DEFAULT_SENDING_INTERVAL: Duration =  (30L).toDuration(DurationUnit.MINUTES)
    const val DEFAULT_TEMPLATE: String = "{CONTACT_NAME}, I AM IN DANGER! My location: {LOCATION_URL}"
    const val FAKE_CONTACT_NAME: String = "John"
    const val FAKE_LOCATION_URL: String = "https://goo.gl/maps/ZNs7dUj4yPbFMgGv6"
    val FAKE_MESSAGE_TEMPLATE: String =
        """
                            {CONTACT_NAME}, I AM IN DANGER!
                            My location:
                            {LOCATION_URL}
                            You can send these commands:
                            {MESSAGE_COMMANDS}
                        """.trimIndent()
    val FAKE_MESSAGE: String =  FAKE_MESSAGE_TEMPLATE.replace("{CONTACT_NAME}", FAKE_CONTACT_NAME)
        .replace("{LOCATION_URL}", FAKE_LOCATION_URL)
        .replace("{MESSAGE_COMMANDS}", messageCommandsAndDescriptionsString)
}