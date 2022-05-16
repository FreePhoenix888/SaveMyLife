package com.freephoenix888.savemylife.data.storage.daos

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy

interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg objects: T): List<Long>

    @Delete
    suspend fun delete(vararg objects: T): Int
}