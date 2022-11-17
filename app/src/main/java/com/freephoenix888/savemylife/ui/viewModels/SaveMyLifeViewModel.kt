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
    getIsAlarmModeEnabledFlowUseCase: GetIsAlarmModeEnabledFlowUseCase,
    val switchIsAlarmModeEnabledUseCase: SwitchIsAlarmModeEnabledUseCase,
    val setIsAlarmModeEnabledUseCase: SetIsAlarmModeEnabledUseCase,
    getIsFirstAppLaunch: GetIsFirstAppLaunchFlowUseCase,
) : ViewModel() {
    val isFirstAppLaunch = getIsFirstAppLaunch()
    val isMainServiceEnabled = getIsMainServiceEnabledFlowUseCase()
    fun switchIsMainServiceEnabled() = viewModelScope.launch(Dispatchers.IO) {
        switchIsMainServiceEnabledUseCase()
    }

    val isAlarmModeEnabled = getIsAlarmModeEnabledFlowUseCase()
    fun switchIsAlarmModeEnabled() = viewModelScope.launch(Dispatchers.IO) {
        switchIsAlarmModeEnabledUseCase()
    }
    fun setIsAlarmModeEnabled(newValue: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        setIsAlarmModeEnabledUseCase(newValue)
    }
}