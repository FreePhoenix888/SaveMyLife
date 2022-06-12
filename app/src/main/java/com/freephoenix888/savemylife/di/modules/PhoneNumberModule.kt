package com.freephoenix888.savemylife.di.modules

import android.content.Context
import androidx.room.Room
import com.freephoenix888.savemylife.data.room.databases.PhoneNumberDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PhoneNumberModule {
    @Singleton
    @Provides
    fun providePhoneNumberRoomDatabase(@ApplicationContext applicationContext: Context): PhoneNumberDatabase {
        return Room.databaseBuilder(
            applicationContext,
            PhoneNumberDatabase::class.java,
            "contact_phone_number.db"
        ).build()
    }
}