package com.freephoenix888.savemylife.domain.useCases

import com.freephoenix888.savemylife.data.repositories.SaveMyLifeRepository
import javax.inject.Inject

class SetDangerModeStateUseCase @Inject constructor(val saveMyLifeRepository: SaveMyLifeRepository){
    suspend operator fun invoke(newState: Boolean) {
        saveMyLifeRepository.setIsDangerModeEnabled(newState)
    }
}