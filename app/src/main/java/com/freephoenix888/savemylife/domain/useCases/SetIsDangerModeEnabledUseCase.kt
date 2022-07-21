package com.freephoenix888.savemylife.domain.useCases

import com.freephoenix888.savemylife.data.repositories.SaveMyLifeRepository
import javax.inject.Inject

class SetIsDangerModeEnabledUseCase @Inject constructor(val saveMyLifeRepository: SaveMyLifeRepository) {
    suspend operator fun invoke(value: Boolean){
        saveMyLifeRepository.setIsDangerModeEnabled(value)
    }
}