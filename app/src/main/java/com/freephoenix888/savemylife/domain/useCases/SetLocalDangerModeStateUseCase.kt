package com.freephoenix888.savemylife.domain.useCases

import android.content.Context
import com.freephoenix888.savemylife.constants.SharedPreferencesConstants
import com.freephoenix888.savemylife.domain.useCases.interfaces.SetDangerModeStateUseCase

class SetLocalDangerModeStateUseCase(val context: Context) :
    SetDangerModeStateUseCase {
    override operator fun invoke(state: Boolean) {
        val sharedPreferences = context.getSharedPreferences(
            SharedPreferencesConstants.SHARED_PREFERENCES_FILE_PATH,
            Context.MODE_PRIVATE
        )
        sharedPreferences.edit()
            .putBoolean(SharedPreferencesConstants.DANGER_MODE_ENABLED, state)
            .apply()
    }
}