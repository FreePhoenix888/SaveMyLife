package com.freephoenix888.savemylife.data.repositories

import com.freephoenix888.savemylife.data.db.ContactDatabase
import com.freephoenix888.savemylife.data.db.entities.ContactEntity
import kotlinx.coroutines.flow.Flow

class ContactRepository(private val db: ContactDatabase) {

    suspend fun insert(vararg contacts: ContactEntity): Int{
        return db.dao().insert(*contacts)
    }

    suspend fun remove(vararg contacts: ContactEntity): Int {
        return db.dao().remove(*contacts)
    }

    suspend fun get(vararg contactIds: Int): Flow<List<ContactEntity>>{
        return db.dao().get(*contactIds)
    }

    fun getAll(): Flow<List<ContactEntity>> = db.dao().getAll()

}