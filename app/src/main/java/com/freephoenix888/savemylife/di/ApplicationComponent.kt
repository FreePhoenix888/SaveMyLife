package com.freephoenix888.savemylife.di

import com.freephoenix888.savemylife.data.repositories.ContactRepository
import com.freephoenix888.savemylife.data.room.databases.ContactDatabase
import com.freephoenix888.savemylife.domain.useCases.interfaces.DeleteContactsUseCase
import com.freephoenix888.savemylife.domain.useCases.interfaces.SaveContactsUseCase
import com.freephoenix888.savemylife.services.MainService
import com.freephoenix888.savemylife.ui.SaveMyLifeActivity
import com.freephoenix888.savemylife.ui.viewModels.ContactsViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [SubcomponentsModule::class, SaveMyLifeModule::class])
interface ApplicationComponent {
    fun mainServiceComponent(): MainServiceComponent.Factory
    fun inject(activity: SaveMyLifeActivity)
    fun inject(repository: ContactRepository)
    fun inject(viewModel: ContactsViewModel)
    fun inject(contactDatabase: ContactDatabase)
    fun inject(saveContactsUseCase: SaveContactsUseCase)
    fun inject(deleteContactsUseCase: DeleteContactsUseCase)
    fun inject(mainService: MainService)
}