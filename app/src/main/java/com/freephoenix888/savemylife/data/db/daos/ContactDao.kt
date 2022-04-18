package com.freephoenix888.savemylife.data.db.daos

import androidx.room.*
import com.freephoenix888.savemylife.data.db.entities.ContactEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao: BaseDao<ContactEntity> {

    @Query("SELECT * FROM contact WHERE id IN (:ids)")
    suspend fun get(vararg ids: Int): Flow<List<ContactEntity>>

    @Query("SELECT * FROM contact ORDER BY id ASC")
    fun getAll(): Flow<List<ContactEntity>>

}