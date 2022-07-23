package com.freephoenix888.savemylife.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freephoenix888.savemylife.domain.useCases.GetLocationSettingsFlowUseCase
import com.freephoenix888.savemylife.domain.useCases.SetIsLocationSharingEnabledUseCase
import com.freephoenix888.savemylife.mappers.LocationSettingsToLocationSettingsFormStateMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationSharingSettingsViewModel @Inject constructor(
    getLocationSettingsFlowUseCase: GetLocationSettingsFlowUseCase,
    private val setIsLocationSharingEnabledUseCase: SetIsLocationSharingEnabledUseCase,
    private val locationSettingsToLocationSettingsFormStateMapper: LocationSettingsToLocationSettingsFormStateMapper
) : ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getLocationSettingsFlowUseCase().collect {
                isLocationSharingEnabled.value = it.isLocationSharingEnabled
            }
        }
    }

    val isLocationSharingEnabled = MutableStateFlow(false)
    fun setIsLocationSharingEnabled(newIsLocationSharingEnabled: Boolean) = viewModelScope.launch {
        isLocationSharingEnabled.value = newIsLocationSharingEnabled
    }
    fun submitIsLocationSharingEnabled() = viewModelScope.launch {
        setIsLocationSharingEnabledUseCase(isLocationSharingEnabled.value)
    }

}
