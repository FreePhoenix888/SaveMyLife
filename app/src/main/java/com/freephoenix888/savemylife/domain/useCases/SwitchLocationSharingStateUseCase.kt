package com.freephoenix888.savemylife.domain.useCases

import com.freephoenix888.savemylife.data.repositories.LocationRepository
import com.freephoenix888.savemylife.data.repositories.MainServiceRepository
import kotlinx.coroutines.flow.last
import javax.inject.Inject

class SwitchLocationSharingStateUseCase @Inject constructor(val repository: LocationRepository) {
    suspend operator fun invoke() {
        val newState = !repository.locationSharingState.last()
        repository.setLocationSharingState(newState)
    }
}