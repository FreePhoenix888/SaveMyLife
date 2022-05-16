package com.freephoenix888.savemylife.data.storage.daos

import androidx.room.*
import com.freephoenix888.savemylife.data.storage.entities.ContactEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao: BaseDao<ContactEntity> {

    @Query("SELECT * FROM contact")
    fun getAll(): Flow<List<ContactEntity>>

}