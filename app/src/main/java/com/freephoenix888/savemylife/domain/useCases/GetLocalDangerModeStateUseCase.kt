package com.freephoenix888.savemylife.domain.useCases

import android.content.Context
import com.freephoenix888.savemylife.constants.SharedPreferencesConstants
import com.freephoenix888.savemylife.domain.useCases.interfaces.GetDangerModeStateUseCase
import javax.inject.Inject

class GetLocalDangerModeStateUseCase @Inject constructor(val context: Context) :
    GetDangerModeStateUseCase {
    override operator fun invoke(): Boolean {
        val sharedPreferences = context.getSharedPreferences(
            SharedPreferencesConstants.SHARED_PREFERENCES_FILE_PATH,
            Context.MODE_PRIVATE
        )
        return sharedPreferences.getBoolean(SharedPreferencesConstants.DANGER_MODE_ENABLED, false)
    }
}