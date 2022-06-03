package com.freephoenix888.savemylife.domain.useCases

import com.freephoenix888.savemylife.data.repositories.MainServiceRepository
import kotlinx.coroutines.flow.last
import javax.inject.Inject

class SwitchDangerModeStateUseCase @Inject constructor(
    val repository: MainServiceRepository
) {
    suspend operator fun invoke() {
        val newState = !repository.dangerModeState.last()
        repository.setDangerModeState(newState)
    }
}