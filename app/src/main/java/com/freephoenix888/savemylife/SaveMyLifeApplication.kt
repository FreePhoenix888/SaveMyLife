package com.freephoenix888.savemylife

import android.app.Application
import com.freephoenix888.savemylife.di.DaggerApplicationComponent
import com.freephoenix888.savemylife.di.SaveMyLifeModule


class SaveMyLifeApplication() : Application() {
    val appComponent = DaggerApplicationComponent.builder()
        .saveMyLifeModule(SaveMyLifeModule(application = this))
        .build()
}
