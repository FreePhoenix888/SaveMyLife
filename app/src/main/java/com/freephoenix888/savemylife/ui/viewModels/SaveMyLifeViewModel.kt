package com.freephoenix888.savemylife.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freephoenix888.savemylife.domain.useCases.GetIsDangerModeEnabledFlowUseCase
import com.freephoenix888.savemylife.domain.useCases.GetIsMainServiceEnabledFlowUseCase
import com.freephoenix888.savemylife.domain.useCases.SwitchIsDangerModeEnabledUseCase
import com.freephoenix888.savemylife.domain.useCases.SwitchIsMainServiceEnabledUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class   SaveMyLifeViewModel @Inject constructor(
    val getIsMainServiceEnabledFlowUseCase: GetIsMainServiceEnabledFlowUseCase,
    val switchIsMainServiceEnabledUseCase: SwitchIsMainServiceEnabledUseCase,
    val getIsDangerModeEnabledFlowUseCase: GetIsDangerModeEnabledFlowUseCase,
    val switchIsDangerModeEnabledUseCase: SwitchIsDangerModeEnabledUseCase
) : ViewModel() {

    val isMainServiceEnabled = getIsMainServiceEnabledFlowUseCase()
    fun switchIsMainServiceEnabled() = viewModelScope.launch {
        switchIsMainServiceEnabledUseCase()
    }

    val isDangerModeEnabled = getIsDangerModeEnabledFlowUseCase()
    fun switchIsDangerModeEnabled() = viewModelScope.launch {
        switchIsDangerModeEnabledUseCase()
    }
}