package com.freephoenix888.savemylife.data.repositories

import com.freephoenix888.savemylife.data.storage.local.ContactLocalStorage
import com.freephoenix888.savemylife.data.storage.entities.ContactEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContactRepository @Inject constructor (val localStorage: ContactLocalStorage) {

    suspend fun insert(vararg contacts: ContactEntity): List<Long>{
        return localStorage.dao().insert(*contacts)
    }

    suspend fun delete(vararg contacts: ContactEntity): Int {
        return localStorage.dao().delete(*contacts)
    }

    val allContacts: Flow<List<ContactEntity>> = localStorage.dao().getAll()

}