package com.freephoenix888.savemylife.data.db.daos

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy

interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg objects: T): Int

    @Delete
    suspend fun remove(vararg objects: T): Int
}