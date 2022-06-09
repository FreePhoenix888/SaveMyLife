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
object EmergencyContactModule {
    @Singleton
    @Provides
    fun provideContactRoomDatabase(@ApplicationContext applicationContext: Context): ContactDatabase {
        return Room.databaseBuilder(
            applicationContext,
            ContactDatabase::class.java,
            "contact.db"
        ).build()
    }
}