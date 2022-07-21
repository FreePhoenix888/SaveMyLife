package com.freephoenix888.savemylife.mappers

import com.freephoenix888.savemylife.domain.models.MessageSettings
import com.freephoenix888.savemylife.domain.models.MessageSettingsFormState
import javax.inject.Inject

class MessageSettingsToMessageSettingsFormStateMapper @Inject constructor(): Mapper<MessageSettings, MessageSettingsFormState> {
    override fun map(input: MessageSettings): MessageSettingsFormState {
        return MessageSettingsFormState(
            template = input.template,
            sendingIntervalInMinutes = input.sendingInterval.inWholeSeconds.toString()
        )
    }
}