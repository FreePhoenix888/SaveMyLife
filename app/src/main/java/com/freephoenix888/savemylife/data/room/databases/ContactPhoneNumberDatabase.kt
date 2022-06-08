package com.freephoenix888.savemylife.data.room.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.freephoenix888.savemylife.data.room.ContactConverter
import com.freephoenix888.savemylife.data.room.daos.ContactPhoneNumberDao
import com.freephoenix888.savemylife.data.room.entities.ContactPhoneNumberEntity

@Database(
    entities = [ContactPhoneNumberEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(ContactConverter::class)
abstract class ContactPhoneNumberDatabase: RoomDatabase() {

    abstract fun dao(): ContactPhoneNumberDao
}