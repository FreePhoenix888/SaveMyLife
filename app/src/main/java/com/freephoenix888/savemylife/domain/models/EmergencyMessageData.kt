package com.freephoenix888.savemylife.domain.models

import com.freephoenix888.savemylife.SecondsInterval
import kotlinx.coroutines.flow.Flow

data class EmergencyMessageData(
    val messageTemplate: Flow<String>,
    val sendingIntervalInSeconds: Flow<SecondsInterval>,
)
