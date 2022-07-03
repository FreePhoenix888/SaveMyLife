package com.freephoenix888.savemylife.domain.useCases

import android.util.Log
import com.freephoenix888.savemylife.data.repositories.SaveMyLifeRepository
import javax.inject.Inject

class SetDangerModeStateUseCase @Inject constructor(val saveMyLifeRepository: SaveMyLifeRepository){
    suspend operator fun invoke(newState: Boolean) {
        Log.d(null, "new isDangerModeState: $newState")
        saveMyLifeRepository.setIsDangerModeEnabled(newState)
    }
}