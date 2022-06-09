package com.freephoenix888.savemylife.di.modules

import android.content.Context
import androidx.room.Room
import com.freephoenix888.savemylife.data.room.databases.ContactPhoneNumberDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object EmergencyContactPhoneNumberModule {
    @Singleton
    @Provides
    fun provideContactPhoneNumberRoomDatabase(@ApplicationContext applicationContext: Context): ContactPhoneNumberDatabase {
        return Room.databaseBuilder(
            applicationContext,
            ContactPhoneNumberDatabase::class.java,
            "contact_phone_number.db"
        ).build()
    }
}