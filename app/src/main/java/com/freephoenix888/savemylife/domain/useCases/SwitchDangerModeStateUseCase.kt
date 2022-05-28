package com.freephoenix888.savemylife.domain.useCases

import com.freephoenix888.savemylife.data.repositories.MainServiceRepository
import javax.inject.Inject

class SwitchDangerModeStateUseCase @Inject constructor(
    val repository: MainServiceRepository
) {
    operator fun invoke() {
        repository.dangerModeState = !(repository.dangerModeState)
    }
}