package com.freephoenix888.savemylife.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freephoenix888.savemylife.domain.useCases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class   SaveMyLifeViewModel @Inject constructor(
    getIsMainServiceEnabledFlowUseCase: GetIsMainServiceEnabledFlowUseCase,
    val switchIsMainServiceEnabledUseCase: SwitchIsMainServiceEnabledUseCase,
    getIsDangerModeEnabledFlowUseCase: GetIsDangerModeEnabledFlowUseCase,
    val switchIsDangerModeEnabledUseCase: SwitchIsDangerModeEnabledUseCase,
    getIsFirstAppLaunch: GetIsFirstAppLaunchFlowUseCase,
) : ViewModel() {
    val isFirstAppLaunch = getIsFirstAppLaunch()
    val isMainServiceEnabled = getIsMainServiceEnabledFlowUseCase()
    fun switchIsMainServiceEnabled() = viewModelScope.launch(Dispatchers.IO) {
        switchIsMainServiceEnabledUseCase()
    }

    val isDangerModeEnabled = getIsDangerModeEnabledFlowUseCase()
    fun switchIsDangerModeEnabled() = viewModelScope.launch(Dispatchers.IO) {
        switchIsDangerModeEnabledUseCase()
    }
}