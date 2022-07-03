package com.freephoenix888.savemylife.domain.useCases

import com.freephoenix888.savemylife.SaveMyLifePreferences
import com.freephoenix888.savemylife.data.repositories.SaveMyLifeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSaveMyLifeSettingsFlowUseCase @Inject constructor(val saveMyLifeRepository: SaveMyLifeRepository) {
    operator fun invoke(): Flow<SaveMyLifePreferences> {
        return saveMyLifeRepository.settings
    }
}
