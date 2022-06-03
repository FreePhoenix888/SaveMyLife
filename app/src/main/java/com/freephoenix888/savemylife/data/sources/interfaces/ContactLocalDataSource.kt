package com.freephoenix888.savemylife.data.sources.interfaces

import com.freephoenix888.savemylife.data.room.databases.entities.ContactEntity
import kotlinx.coroutines.flow.Flow

interface ContactLocalDataSource {
    val contacts: Flow<List<ContactEntity>>

    suspend fun insert(contacts: List<ContactEntity>): List<Long>

    suspend fun update(contacts: List<ContactEntity>): Int

    suspend fun delete(contacts: List<ContactEntity>): Int
}