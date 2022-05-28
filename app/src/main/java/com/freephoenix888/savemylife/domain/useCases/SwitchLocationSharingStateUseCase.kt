package com.freephoenix888.savemylife.domain.useCases

import com.freephoenix888.savemylife.data.repositories.MainServiceRepository
import javax.inject.Inject

class SwitchLocationSharingStateUseCase @Inject constructor(val repository: MainServiceRepository) {
    operator fun invoke() {
        repository.locationSharingState = !(repository.locationSharingState)
    }
}