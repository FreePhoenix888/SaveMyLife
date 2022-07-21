package com.freephoenix888.savemylife.domain.models

import com.freephoenix888.savemylife.constants.MessageConstants

data class MessageSettingsFormState(
    val template: String = MessageConstants.DEFAULT_TEMPLATE,
    val templateErrorMessage: String? = null,
    val sendingIntervalInMinutes: String = MessageConstants.DEFAULT_SENDING_INTERVAL.inWholeSeconds.toString(),
    val sendingIntervalInMinutesErrorMessage: String? = null,
    )
