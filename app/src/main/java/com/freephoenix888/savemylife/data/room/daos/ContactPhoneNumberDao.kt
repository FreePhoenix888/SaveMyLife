package com.freephoenix888.savemylife.data.room.daos

import androidx.room.Dao
import androidx.room.Query
import com.freephoenix888.savemylife.data.room.entities.ContactPhoneNumberEntity
import com.freephoenix888.savemylife.data.storage.daos.BaseDao
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactPhoneNumberDao: BaseDao<ContactPhoneNumberEntity> {

    @Query("SELECT * FROM contact_phone_number")
    fun getContactPhoneNumbers(): Flow<List<ContactPhoneNumberEntity>>
}
