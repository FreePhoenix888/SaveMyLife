package com.freephoenix888.savemylife.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freephoenix888.savemylife.domain.models.LocationSettingsUiState
import com.freephoenix888.savemylife.domain.useCases.GetLocationSettingsFlowUseCase
import com.freephoenix888.savemylife.domain.useCases.SetIsLocationSharingEnabledUseCase
import com.freephoenix888.savemylife.mappers.LocationSettingsToLocationSettingsFormStateMapper
import com.freephoenix888.savemylife.ui.LocationSettingsFormEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationSettingsViewModel @Inject constructor(
    getLocationSettingsFlowUseCase: GetLocationSettingsFlowUseCase,
    private val setIsLocationSharingEnabledUseCase: SetIsLocationSharingEnabledUseCase,
    private val locationSettingsToLocationSettingsFormStateMapper: LocationSettingsToLocationSettingsFormStateMapper
) : ViewModel() {



    init {
        viewModelScope.launch(Dispatchers.IO) {
            getLocationSettingsFlowUseCase().collect {
                _state.value = locationSettingsToLocationSettingsFormStateMapper.map(it)
            }
        }
    }

    private val _state = MutableStateFlow(LocationSettingsUiState())
    val state: StateFlow<LocationSettingsUiState> = _state

    fun onEvent(event: LocationSettingsFormEvent) = viewModelScope.launch {
        if(event !is LocationSettingsFormEvent.Submit) {
            makeErrorMessagesNull()
        }
        when(event) {
            is LocationSettingsFormEvent.IsLocationSharingEnabledChanged -> {
                setIsLocationSharingEnabledUseCase(event.isLocationSharingEnabled)
            }
        }
    }

    private fun makeErrorMessagesNull() {
    }
}
