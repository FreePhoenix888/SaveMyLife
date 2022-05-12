package com.freephoenix888.savemylife.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.freephoenix888.savemylife.data.db.ContactLocalStorage
import com.freephoenix888.savemylife.data.repositories.ContactRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SaveMyLifeModule(val application: Application) {

    @Singleton
    @Provides
    fun provideContactLocalStorage() = ContactLocalStorage.getInstance(application)

    @Singleton
    @Provides
    fun provideYourDao(localStorage: ContactLocalStorage) = localStorage.dao()

    @Singleton
    @Provides
    fun provideContactRepository(contactLocalStorage: ContactLocalStorage) = ContactRepository(contactLocalStorage)
}