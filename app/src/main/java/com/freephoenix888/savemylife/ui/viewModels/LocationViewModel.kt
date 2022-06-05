package com.freephoenix888.savemylife.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freephoenix888.savemylife.domain.useCases.GetLocationSharingStateFlowUseCase
import com.freephoenix888.savemylife.domain.useCases.SetLocationSharingStateUseCase
import com.freephoenix888.savemylife.domain.useCases.SwitchLocationSharingStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    val getLocationSharingStateFlowUseCase: GetLocationSharingStateFlowUseCase,
    val setLocationSharingStateUseCase: SetLocationSharingStateUseCase,
    val switchLocationSharingStateUseCase: SwitchLocationSharingStateUseCase
) : ViewModel() {
    val locationSharingState = getLocationSharingStateFlowUseCase()
    fun setLocationSharingState(newState: Boolean) = viewModelScope.launch {
        setLocationSharingStateUseCase(newState)
    }
    fun switchLocationSharingState() = viewModelScope.launch {
        switchLocationSharingStateUseCase()
    }
}
