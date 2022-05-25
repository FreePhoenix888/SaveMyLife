package com.freephoenix888.savemylife.domain.useCases

import android.content.Context
import com.freephoenix888.savemylife.constants.SharedPreferencesConstants
import com.freephoenix888.savemylife.domain.useCases.interfaces.SwitchLocationSharingStateUseCase
import javax.inject.Inject

class SwitchLocalLocationSharingStateUseCase @Inject constructor(val context: Context) :
    SwitchLocationSharingStateUseCase {
    override operator fun invoke() {
        val sharedPreferences = context.getSharedPreferences(
            SharedPreferencesConstants.SHARED_PREFERENCES_FILE_PATH,
            Context.MODE_PRIVATE
        )
        val isLocationSharingEnabled = sharedPreferences.getBoolean(
            SharedPreferencesConstants.IS_LOCATION_SHARING_ENABLED,
            false
        )
        sharedPreferences.edit()
            .putBoolean(
                SharedPreferencesConstants.IS_LOCATION_SHARING_ENABLED,
                !isLocationSharingEnabled
            )
            .apply()
    }
}