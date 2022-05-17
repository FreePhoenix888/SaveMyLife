package com.freephoenix888.savemylife.di

import com.freephoenix888.savemylife.data.repositories.ContactRepository
import com.freephoenix888.savemylife.data.storage.local.ContactLocalStorage
import com.freephoenix888.savemylife.di.SaveMyLifeModule
import com.freephoenix888.savemylife.domain.useCases.DeleteContactsUseCase
import com.freephoenix888.savemylife.domain.useCases.IDeleteContactsUseCase
import com.freephoenix888.savemylife.domain.useCases.ISaveContactsUseCase
import com.freephoenix888.savemylife.services.MainService
import com.freephoenix888.savemylife.ui.SaveMyLifeActivity
import com.freephoenix888.savemylife.ui.fragments.ContactsSettingsFragment
import com.freephoenix888.savemylife.ui.viewModels.ContactViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [SubcomponentsModule::class, SaveMyLifeModule::class])
interface ApplicationComponent {
    fun mainServiceComponent(): MainServiceComponent.Factory
    fun inject(activity: SaveMyLifeActivity)
    fun inject(repository: ContactRepository)
    fun inject(viewModel: ContactViewModel)
    fun inject(contactLocalStorage: ContactLocalStorage)
    fun inject(contactsSettingsFragment: ContactsSettingsFragment)
    fun inject(saveContactsUseCase: ISaveContactsUseCase)
    fun inject(deleteContactsUseCase: IDeleteContactsUseCase)
    fun inject(mainService: MainService)
}