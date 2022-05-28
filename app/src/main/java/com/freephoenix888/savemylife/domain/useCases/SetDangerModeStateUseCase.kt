package com.freephoenix888.savemylife.domain.useCases

import android.content.Context
import com.freephoenix888.savemylife.constants.SharedPreferencesConstants
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SetDangerModeStateUseCase @Inject constructor(@ApplicationContext val context: Context){
    operator fun invoke(state: Boolean) {
        val sharedPreferences = context.getSharedPreferences(
            SharedPreferencesConstants.FILE_PATH,
            Context.MODE_PRIVATE
        )
        sharedPreferences.edit()
            .putBoolean(SharedPreferencesConstants.DANGER_MODE_STATE, state)
            .apply()
    }
}