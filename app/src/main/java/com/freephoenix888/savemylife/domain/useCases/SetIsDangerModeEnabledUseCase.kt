package com.freephoenix888.savemylife.domain.useCases

import com.freephoenix888.savemylife.data.repositories.SaveMyLifeRepository
import timber.log.Timber
import javax.inject.Inject

class SetIsDangerModeEnabledUseCase @Inject constructor(val saveMyLifeRepository: SaveMyLifeRepository) {
    suspend operator fun invoke(value: Boolean){
        Timber.d( "SetIsDangerModeEnabledUseCase invoke: ${value}")
        saveMyLifeRepository.setIsDangerModeEnabled(value)
    }
}