package com.freephoenix888.savemylife.data.room.daos

import androidx.room.Dao
import androidx.room.Query
import com.freephoenix888.savemylife.data.room.entities.PhoneNumberEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PhoneNumberDao: BaseDao<PhoneNumberEntity> {

    @Query("SELECT * FROM phone_number")
    fun getPhoneNumberList(): Flow<List<PhoneNumberEntity>>
}
