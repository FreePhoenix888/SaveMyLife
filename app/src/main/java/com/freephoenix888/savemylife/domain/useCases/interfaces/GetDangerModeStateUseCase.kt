package com.freephoenix888.savemylife.domain.useCases.interfaces

import android.content.Context

interface GetDangerModeStateUseCase {
    operator fun invoke(): Boolean
}