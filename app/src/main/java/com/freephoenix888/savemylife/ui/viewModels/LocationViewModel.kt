package com.freephoenix888.savemylife.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freephoenix888.savemylife.domain.useCases.GetIsLocationSharingEnabledFlowUseCase
import com.freephoenix888.savemylife.domain.useCases.SetIsLocationSharingEnabledUseCase
import com.freephoenix888.savemylife.domain.useCases.SwitchLocationSharingStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    val getIsLocationSharingEnabledFlowUseCase: GetIsLocationSharingEnabledFlowUseCase,
    val setIsLocationSharingEnabledUseCase: SetIsLocationSharingEnabledUseCase,
    val switchLocationSharingStateUseCase: SwitchLocationSharingStateUseCase
) : ViewModel() {
    val isLocationSharingEnabled = getIsLocationSharingEnabledFlowUseCase()
    fun isLocationSharingEnabled(newState: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        setIsLocationSharingEnabledUseCase(newState)
    }
    fun setIsLocationSharingEnabled(newState: Boolean) =viewModelScope.launch {
        setIsLocationSharingEnabledUseCase(newState)
    }
    fun switchLocationSharingState() = viewModelScope.launch(Dispatchers.IO) {
        switchLocationSharingStateUseCase()
    }
}
