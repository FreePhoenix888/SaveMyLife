package com.freephoenix888.savemylife.data.repositories

import com.freephoenix888.savemylife.data.db.ContactDatabase
import com.freephoenix888.savemylife.data.db.entities.ContactEntity
import kotlinx.coroutines.flow.Flow

class ContactRepository(private val db: ContactDatabase) {

    fun getAll(): Flow<List<ContactEntity>> = db.dao().getAll()

    suspend fun insert(vararg contacts: ContactEntity): Int{
        return db.dao().insert(*contacts)
    }

    suspend fun remove(vararg contacts: ContactEntity): Int {
        return db.dao().remove(*contacts)
    }


}