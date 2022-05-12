package com.freephoenix888.savemylife.data.repositories

import com.freephoenix888.savemylife.data.db.ContactLocalStorage
import com.freephoenix888.savemylife.data.db.entities.ContactEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContactRepository @Inject constructor (val localStorage: ContactLocalStorage) {

    suspend fun insert(vararg contacts: ContactEntity){
        localStorage.dao().insert(*contacts)
    }

    suspend fun remove(vararg contacts: ContactEntity) {
        localStorage.dao().remove(*contacts)
    }

    fun getAll(): Flow<List<ContactEntity>> = localStorage.dao().getAll()

}