package com.freephoenix888.savemylife.domain.useCases

import com.freephoenix888.savemylife.data.repositories.SaveMyLifeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetIsFirstAppLaunchFlowUseCase @Inject constructor(private val saveMyLifeRepository: SaveMyLifeRepository) {
    operator fun invoke(): Flow<Boolean> {
        return saveMyLifeRepository.settings.map {
            it.isFirstAppLaunch
        }
    }
}
