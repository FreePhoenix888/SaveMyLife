package com.freephoenix888.savemylife.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.freephoenix888.savemylife.data.db.entities.Contact

@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(contact: Contact): Int

    @Delete
    suspend fun remove(contact: Contact): Int

    @Query("SELECT * FROM contact WHERE id = :id")
    suspend fun get(id: Int): Contact

    @Query("SELECT * FROM contact ORDER BY id ASC")
    fun getAll(): LiveData<List<Contact>>

}