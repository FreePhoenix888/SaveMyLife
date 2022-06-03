package com.freephoenix888.savemylife.data.sources

import com.freephoenix888.savemylife.data.room.databases.ContactDatabase
import com.freephoenix888.savemylife.data.room.databases.entities.ContactEntity
import com.freephoenix888.savemylife.data.sources.interfaces.ContactLocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContactRoomLocalDataSource @Inject constructor (private val database: ContactDatabase):
    ContactLocalDataSource {
    override val contacts: Flow<List<ContactEntity>>
        get() = database.dao().getAll()

    override suspend fun insert(vararg contacts: ContactEntity): List<Long> {
        return database.dao().insert(*contacts)
    }

    override suspend fun update(vararg contacts: ContactEntity): Int {
        return database.dao().update(*contacts)
    }

    override suspend fun delete(vararg contacts: ContactEntity): Int {
        return database.dao().delete(*contacts)
    }
}