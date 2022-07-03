package com.freephoenix888.savemylife.domain.useCases

import com.freephoenix888.savemylife.data.repositories.SaveMyLifeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetIsDangerModeEnabledFlowUseCase @Inject constructor(private val repository: SaveMyLifeRepository){
    operator fun invoke(): Flow<Boolean> {
        return repository.settings.map { it.isDangerModeEnabled }
    }
}