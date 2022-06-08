package com.freephoenix888.savemylife.data.room.daos

import androidx.room.*
import com.freephoenix888.savemylife.data.room.entities.ContactEntity
import com.freephoenix888.savemylife.data.room.entities.relations.ContactWithPhoneNumbersRelation
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(contacts: ContactEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(contacts: List<ContactEntity>): List<Long>

    @Delete
    suspend fun delete(contacts: ContactEntity)

    @Delete
    suspend fun deleteList(contacts: List<ContactEntity>): Int

    @Query("SELECT * FROM contact")
    fun getContactsWithPhoneNumbers(): Flow<List<ContactWithPhoneNumbersRelation>>
}