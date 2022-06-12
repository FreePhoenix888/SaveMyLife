package com.freephoenix888.savemylife.data.room.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.freephoenix888.savemylife.data.room.PhoneNumberConverter
import com.freephoenix888.savemylife.data.room.daos.PhoneNumberDao
import com.freephoenix888.savemylife.data.room.entities.PhoneNumberEntity

@Database(
    entities = [PhoneNumberEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(PhoneNumberConverter::class)
abstract class PhoneNumberDatabase: RoomDatabase() {

    abstract fun dao(): PhoneNumberDao
}