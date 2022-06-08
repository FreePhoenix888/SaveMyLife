package com.freephoenix888.savemylife.domain.useCases

import com.freephoenix888.savemylife.data.repositories.SaveMyLifeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetIsMainServiceEnabledFlowUseCase @Inject constructor(val saveMyLifeRepository: SaveMyLifeRepository) {
    operator fun invoke(): Flow<Boolean> {
        return saveMyLifeRepository.mainServiceState
    }
}