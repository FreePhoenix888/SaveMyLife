package com.freephoenix888.savemylife.domain.useCases

import android.content.Context
import com.freephoenix888.savemylife.domain.useCases.interfaces.GetDangerModeStateUseCase
import com.freephoenix888.savemylife.domain.useCases.interfaces.SetDangerModeStateUseCase
import com.freephoenix888.savemylife.domain.useCases.interfaces.SwitchDangerModeStateUseCase

class SwitchLocalDangerModeStateUseCase(
    val context: Context,
    val getDangerModeStateUseCase: GetDangerModeStateUseCase,
    val setIsDangerModeEnabledUseCase: SetDangerModeStateUseCase
) : SwitchDangerModeStateUseCase {
    override operator fun invoke() {
        val previousState = getDangerModeStateUseCase()
        setIsDangerModeEnabledUseCase(!previousState)
    }
}