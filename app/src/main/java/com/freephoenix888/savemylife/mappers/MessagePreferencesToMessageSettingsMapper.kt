package com.freephoenix888.savemylife.mappers

import com.freephoenix888.savemylife.MessagePreferences
import com.freephoenix888.savemylife.constants.MessageConstants
import com.freephoenix888.savemylife.domain.models.MessageSettings
import javax.inject.Inject
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class MessagePreferencesToMessageSettingsMapper @Inject constructor(): Mapper<MessagePreferences, MessageSettings> {
    override fun map(input: MessagePreferences): MessageSettings {
        return MessageSettings(
            template = input.template.ifEmpty { MessageConstants.DEFAULT_TEMPLATE },
            sendingInterval = if (input.sendingIntervalInMinutes == 0L) MessageConstants.DEFAULT_SENDING_INTERVAL else input.sendingIntervalInMinutes.toDuration(DurationUnit.MINUTES),
            isMessageCommandsEnabled = input.isMessageCommandsEnabled
        )
    }
}
