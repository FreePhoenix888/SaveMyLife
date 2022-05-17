package com.freephoenix888.savemylife.di

import com.freephoenix888.savemylife.services.MainService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MainServiceModule(val mainService: MainService) {

    @Provides
    fun provideMainService() = mainService
}