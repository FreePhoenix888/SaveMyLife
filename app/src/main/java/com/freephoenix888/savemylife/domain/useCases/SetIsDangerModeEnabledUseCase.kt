package com.freephoenix888.savemylife.domain.useCases

import android.util.Log
import com.freephoenix888.savemylife.data.repositories.SaveMyLifeRepository
import javax.inject.Inject

class SetIsAlarmModeEnabledUseCase @Inject constructor(val saveMyLifeRepository: SaveMyLifeRepository) {
    suspend operator fun invoke(value: Boolean){
        Log.d(null, "SetIsAlarmModeEnabledUseCase invoke: ${value}")
        saveMyLifeRepository.setIsAlarmModeEnabled(value)
    }
}