package com.freephoenix888.savemylife.domain.useCases

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.core.content.ContextCompat

class OpenApplicationSettingsUseCase {
    operator fun invoke(context: Context) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", context.packageName, null)
        intent.data = uri
        ContextCompat.startActivity(context, intent, null)
    }
}