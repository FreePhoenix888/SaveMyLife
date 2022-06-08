package com.freephoenix888.savemylife.data.room.daos

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy

interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(obj: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(obj: List<T>): List<Long>

    @Delete
    suspend fun delete(obj: T)

    @Delete
    suspend fun deleteAll(obj: List<T>): Int
}