package com.freephoenix888.savemylife.ui.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freephoenix888.savemylife.domain.useCases.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SaveMyLifeViewModel @Inject constructor(
    @ApplicationContext val applicationContext: Context,
    getIsMainServiceEnabledFlowUseCase: GetIsMainServiceEnabledFlowUseCase,
    val setIsMainServiceStateUseCase: SetMainServiceStateUseCase,
    getIsDangerModeEnabledFlowUseCase: GetIsDangerModeEnabledFlowUseCase,
    val setIsDangerModeEnabledUseCase: SetIsDangerModeEnabledUseCase,
    getIsFirstAppLaunch: GetIsFirstAppLaunchFlowUseCase,
    val openDangerModeActivationConfirmationScreenUseCase: OpenDangerModeActivationConfirmationScreenUseCase
) : ViewModel() {
    val isFirstAppLaunch = getIsFirstAppLaunch()
    val isMainServiceEnabled = getIsMainServiceEnabledFlowUseCase()
    fun setIsMainServiceEnabled(newValue: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        setIsMainServiceStateUseCase(newValue)
    }

    val isDangerModeEnabled = getIsDangerModeEnabledFlowUseCase()
    fun setIsDangerModeEnabled(newValue: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        setIsDangerModeEnabledUseCase(newValue)
    }

    fun openDangerModeActivationConfirmationScreen() {
        openDangerModeActivationConfirmationScreenUseCase(applicationContext)
    }
}