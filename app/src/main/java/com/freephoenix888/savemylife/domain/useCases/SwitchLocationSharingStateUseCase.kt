package com.freephoenix888.savemylife.domain.useCases

import android.content.Context
import androidx.core.content.ContentProviderCompat.requireContext
import com.freephoenix888.savemylife.constants.PreferencesConstants

class SwitchLocationSharingStateUseCase : ISwitchLocationSharingStateUseCase {
    override operator fun invoke(context: Context){
        val preferences = context.getSharedPreferences(
            PreferencesConstants.PREFERENCES_FILE_PATH,
            Context.MODE_PRIVATE
        )
        val isLocationSharingEnabled = preferences.getBoolean(PreferencesConstants.IS_LOCATION_SHARING_ENABLED, false)
        preferences.edit()
            .putBoolean(PreferencesConstants.IS_LOCATION_SHARING_ENABLED, !isLocationSharingEnabled)
            .apply()
    }
}