package com.freephoenix888.savemylife.data.db.daos

import androidx.room.*
import com.freephoenix888.savemylife.data.db.entities.ContactEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@Dao
interface ContactDao: BaseDao<ContactEntity> {

    @Query("SELECT * FROM contact")
    fun getAll(): Flow<List<ContactEntity>>

}