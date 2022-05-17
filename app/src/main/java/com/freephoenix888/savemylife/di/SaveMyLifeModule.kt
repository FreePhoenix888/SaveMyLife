package com.freephoenix888.savemylife.di

import android.app.Application
import androidx.room.Room
import com.freephoenix888.savemylife.data.storage.local.ContactLocalStorage
import com.freephoenix888.savemylife.data.repositories.ContactRepository
import com.freephoenix888.savemylife.domain.useCases.DeleteContactsUseCase
import com.freephoenix888.savemylife.domain.useCases.IDeleteContactsUseCase
import com.freephoenix888.savemylife.domain.useCases.ISaveContactsUseCase
import com.freephoenix888.savemylife.domain.useCases.SaveContactsUseCase
import com.freephoenix888.savemylife.ui.viewModels.ContactViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SaveMyLifeModule(val application: Application) {

    @Singleton
    @Provides
    fun provideContactLocalStorage(): ContactLocalStorage {
        return Room.databaseBuilder(
            application,
            ContactLocalStorage::class.java,
            "Contact.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideContactViewModel(repository: ContactRepository, saveContactsUseCase: ISaveContactsUseCase, deleteContactsUseCase: IDeleteContactsUseCase) = ContactViewModelFactory(repository = repository, saveContactsUseCase = saveContactsUseCase, deleteContactsUseCase = deleteContactsUseCase)

    @Singleton
    @Provides
    fun provideYourDao(localStorage: ContactLocalStorage) = localStorage.dao()

    @Singleton
    @Provides
    fun provideContactRepository(contactLocalStorage: ContactLocalStorage) = ContactRepository(contactLocalStorage)

    @Singleton
    @Provides
    fun provideSaveContactsUseCase(): ISaveContactsUseCase {
        return SaveContactsUseCase()
    }

    @Singleton
    @Provides
    fun provideDeleteContactsUseCase(): IDeleteContactsUseCase {
        return DeleteContactsUseCase()
    }
}