package com.freephoenix888.savemylife.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.freephoenix888.savemylife.data.repositories.ContactRepository
import com.freephoenix888.savemylife.data.room.databases.ContactDatabase
import com.freephoenix888.savemylife.data.sources.ContactsLocalDataSource
import com.freephoenix888.savemylife.domain.useCases.DeleteLocalContactsUseCase
import com.freephoenix888.savemylife.domain.useCases.GetLocalDangerModeStateUseCase
import com.freephoenix888.savemylife.domain.useCases.SaveLocalContactsUseCase
import com.freephoenix888.savemylife.domain.useCases.interfaces.DeleteContactsUseCase
import com.freephoenix888.savemylife.domain.useCases.interfaces.GetContactByUriUseCase
import com.freephoenix888.savemylife.domain.useCases.interfaces.GetDangerModeStateUseCase
import com.freephoenix888.savemylife.domain.useCases.interfaces.SaveContactsUseCase
import com.freephoenix888.savemylife.ui.SaveMyLifeActivity
import com.freephoenix888.savemylife.ui.viewModels.ContactsViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import javax.inject.Singleton

@Module
@InstallIn(SaveMyLifeActivity::class)
abstract class SaveMyLifeModule(val application: Application) {

    @Singleton
    @Provides
    fun provideContactLocalStorage(): ContactDatabase {
        return Room.databaseBuilder(
            application,
            ContactDatabase::class.java,
            "Contact.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideContactViewModel(
        repository: ContactRepository,
        saveContactsUseCase: SaveContactsUseCase,
        deleteContactsUseCase: DeleteContactsUseCase,
        getContactByUriUseCase: GetContactByUriUseCase
    ) = ContactsViewModelFactory(
        repository = repository,
        saveContactsUseCase = saveContactsUseCase,
        deleteContactsUseCase = deleteContactsUseCase,
        getContactByUriUseCase = getContactByUriUseCase
    )

    @Singleton
    @Provides
    fun provideYourDao(localStorage: ContactDatabase) = localStorage.dao()

    @Singleton
    @Provides
    fun provideContactRepository(contactDatabase: ContactDatabase) =
        ContactRepository(ContactsLocalDataSource(database = contactDatabase))

    @Singleton
    @Provides
    fun provideSaveContactsUseCase(repository: ContactRepository): SaveContactsUseCase {
        return SaveLocalContactsUseCase()
    }

    @Singleton
    @Provides
    fun provideDeleteContactsUseCase(): DeleteContactsUseCase {
        return DeleteLocalContactsUseCase()
    }

    @Singleton
    @Provides
    fun provideGetIsDangerModeEnabledUseCase(): GetDangerModeStateUseCase {
        return GetLocalDangerModeStateUseCase()
    }

    @Singleton
    @Provides
    fun provideIsDangerModeEnabledInitialValue(
        context: Context,
        getDangerModeStateUseCase: GetDangerModeStateUseCase
    ): Boolean {
        return getDangerModeStateUseCase(context)
    }
}