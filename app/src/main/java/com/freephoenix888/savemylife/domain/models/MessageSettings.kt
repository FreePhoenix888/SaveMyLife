package com.freephoenix888.savemylife.domain.models


import kotlin.time.Duration

data class MessageSettings(
    val template: String,
    val sendingInterval: Duration,
    val isMessageCommandsEnabled: Boolean,
)
