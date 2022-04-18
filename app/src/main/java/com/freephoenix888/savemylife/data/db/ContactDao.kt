package com.freephoenix888.savemylife.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.freephoenix888.savemylife.data.db.entities.Contact
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertContacts(vararg contacts: Contact): Int

    @Delete
    suspend fun removeContacts(vararg contacts: Contact): Int

    @Query("SELECT * FROM contact WHERE id IN (:ids)")
    suspend fun get(vararg ids: Int): Flow<List<Contact>>

    @Query("SELECT * FROM contact ORDER BY id ASC")
    fun getAll(): Flow<List<Contact>>

}