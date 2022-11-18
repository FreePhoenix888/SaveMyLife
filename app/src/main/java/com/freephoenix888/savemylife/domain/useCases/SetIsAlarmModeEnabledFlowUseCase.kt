package com.freephoenix888.savemylife.domain.useCases

import com.freephoenix888.savemylife.data.repositories.SaveMyLifeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SetIsAlarmModeEnabledFlowUseCase @Inject constructor(private val repository: SaveMyLifeRepository){
    suspend operator fun invoke(newValue: Boolean) {
        repository.setIsAlarmModeEnabled(newValue)
    }
}