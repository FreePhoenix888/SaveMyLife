package com.freephoenix888.savemylife.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freephoenix888.savemylife.domain.models.LocationSettingsFormState
import com.freephoenix888.savemylife.domain.useCases.GetLocationSettingsFlowUseCase
import com.freephoenix888.savemylife.domain.useCases.SetIsLocationSharingEnabledUseCase
import com.freephoenix888.savemylife.mappers.LocationSettingsToLocationSettingsFormStateMapper
import com.freephoenix888.savemylife.ui.LocationSettingsFormEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
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

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    private val _state = MutableStateFlow(LocationSettingsFormState())
    val state: StateFlow<LocationSettingsFormState> = _state

    fun onEvent(event: LocationSettingsFormEvent) {
        if(event !is LocationSettingsFormEvent.Submit) {
            makeErrorMessagesNull()
        }
        when(event) {
            is LocationSettingsFormEvent.IsLocationSharingEnabledChanged -> {
                _state.value = _state.value.copy(isLocationSharingEnabled = event.isLocationSharingEnabled)
            }
            is LocationSettingsFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun makeErrorMessagesNull() {
    }

    private fun submitData() {
        viewModelScope.launch {
            setIsLocationSharingEnabledUseCase(_state.value.isLocationSharingEnabled)
        }
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }



    sealed class ValidationEvent{
        object Success: ValidationEvent()
    }
}
