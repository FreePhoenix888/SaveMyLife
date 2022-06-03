package com.freephoenix888.savemylife.domain.useCases

import android.content.Context
import com.freephoenix888.savemylife.data.datastore.MainServicePreferencesSerializer.mainServicePreferencesDataStore
import com.freephoenix888.savemylife.data.repositories.MainServiceRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.last
import javax.inject.Inject

class SetDangerModeStateUseCase @Inject constructor(val mainServiceRepository: MainServiceRepository){
    suspend operator fun invoke(newState: Boolean) {
        mainServiceRepository.setDangerModeState(newState)
    }
}