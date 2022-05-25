package com.freephoenix888.savemylife.domain.useCases.interfaces

import android.content.Context

interface SetDangerModeStateUseCase {
    operator fun invoke(state: Boolean)
}