package com.freephoenix888.savemylife.di.modules

import android.content.Context
import androidx.room.Room
import com.freephoenix888.savemylife.data.room.databases.ContactDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SaveMyLifeModule {

    @Singleton
    @Provides
    fun provideContactRoomStorage(@ApplicationContext applicationContext: Context): ContactDatabase {
        return Room.databaseBuilder(
            applicationContext,
            ContactDatabase::class.java,
            "Contact.db"
        ).build()
    }

//    @Singleton
//    @Provides
//    fun provideYourDao(localStorage: ContactDatabase) = localStorage.dao()

//    @Singleton
//    @Provides
//    fun provideSaveLocalContactsUseCase(repository: ContactRepository): SaveEmergencyContactsUseCase {
//        return SaveEmergencyContactsUseCase(repository = repository)
//    }
//
//    @Singleton
//    @Provides
//    fun provideDeleteContactsUseCase(repository: ContactRepository): DeleteEmergencyContactsUseCase {
//        return DeleteEmergencyContactsUseCase(repository = repository)
//    }
//
//    @Singleton
//    @Provides
//    fun provideGetDangerModeStateUseCase(repository: MainServiceRepository) =
//        GetDangerModeStateUseCase(repository = repository)
//
//    @Singleton
//    @Provides
//    fun provideSetDangerModeStateUseCase(@ApplicationContext applicationContext: Context) =
//        SetDangerModeStateUseCase(context = applicationContext)
//
//    @Singleton
//    @Provides
//    fun provideSwitchDangerModeStateUseCase(repository: MainServiceRepository) =
//        SwitchDangerModeStateUseCase(repository = repository)
//
//    @Singleton
//    @Provides
//    fun provideSwitchLocationSharingStateUseCase(repository: MainServiceRepository) =
//        SwitchLocationSharingStateUseCase(repository = repository)
//
//    @Singleton
//    @Provides
//    fun provideGetContactPhoneNumbersByIdUseCase(@ApplicationContext applicationContext: Context) =
//        GetContactPhoneNumbersByIdUseCase(context = applicationContext)
//
//    @Singleton
//    @Provides
//    fun provideGetContactByUriUseCase(
//        @ApplicationContext applicationContext: Context,
//        getContactPhoneNumbersByIdUseCase: GetContactPhoneNumbersByIdUseCase
//    ) =
//        GetContactByUriUseCase(
//            context = applicationContext,
//            getContactPhoneNumbersByIdUseCase = getContactPhoneNumbersByIdUseCase
//        )
}