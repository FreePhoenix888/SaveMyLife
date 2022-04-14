package com.freephoenix888.savemylife.data.repositories

import androidx.lifecycle.LiveData
import com.freephoenix888.savemylife.data.db.ContactDatabase
import com.freephoenix888.savemylife.data.db.entities.Contact

class ContactRepository(private val db: ContactDatabase) {

    fun getAll(): LiveData<List<Contact>> = db.dao().getAll()

    suspend fun add(contact: Contact): Int{
        return db.dao().add(contact)
    }

    suspend fun remove(contact: Contact): Int {
        return db.dao().remove(contact)
    }


}