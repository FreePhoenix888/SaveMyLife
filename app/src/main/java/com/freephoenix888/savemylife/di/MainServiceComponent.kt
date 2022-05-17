package com.freephoenix888.savemylife.di

import com.freephoenix888.savemylife.services.MainService
import dagger.Subcomponent

@Subcomponent
interface MainServiceComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): MainServiceComponent
    }

    fun inject(mainService: MainService)
}