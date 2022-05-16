package com.freephoenix888.savemylife

import android.app.Application
import com.freephoenix888.savemylife.data.db.ContactLocalStorage
import com.freephoenix888.savemylife.data.repositories.ContactRepository
import com.freephoenix888.savemylife.di.SaveMyLifeModule
import com.freephoenix888.savemylife.fragments.ContactsSettingsFragment
import com.freephoenix888.savemylife.ui.ContactViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [SaveMyLifeModule::class])
interface ApplicationComponent {
    fun inject(activity: SaveMyLifeActivity)
    fun inject(repository: ContactRepository)
    fun inject(viewModel: ContactViewModel)
    fun inject(contactLocalStorage: ContactLocalStorage)
    fun inject(contactsSettingsFragment: ContactsSettingsFragment)
}


class SaveMyLifeApplication() : Application() {
    val appComponent = DaggerApplicationComponent.builder()
        .saveMyLifeModule(SaveMyLifeModule(application = this))
        .build()
}
