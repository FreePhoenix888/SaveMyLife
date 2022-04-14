package com.freephoenix888.savemylife

import android.app.Application
import com.freephoenix888.savemylife.data.db.ContactDatabase
import com.freephoenix888.savemylife.data.repositories.ContactRepository
import com.freephoenix888.savemylife.ui.ContactViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class SaveMyLifeApplication() : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@SaveMyLifeApplication))
        bind() from singleton { ContactDatabase(instance()) }
        bind() from singleton { ContactRepository(instance()) }
        bind() from provider {
            ContactViewModelFactory(instance())
        }
    }
}