package com.freephoenix888.savemylife.domain.useCases

import android.content.Context
import android.content.Intent
import com.freephoenix888.savemylife.enums.IntentAction
import com.freephoenix888.savemylife.ui.SaveMyLifeActivity
import javax.inject.Inject

class OpenDangerModeActivationConfirmationScreenUseCase @Inject constructor() {
    operator fun invoke(context: Context) {
        val saveMyLifeActivityIntent = Intent(context, SaveMyLifeActivity::class.java)
        saveMyLifeActivityIntent.action = IntentAction.EnableDangerMode.name
        saveMyLifeActivityIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        saveMyLifeActivityIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        context.startActivity(saveMyLifeActivityIntent)
    }
}