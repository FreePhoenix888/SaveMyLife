package com.freephoenix888.savemylife.data.room.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.freephoenix888.savemylife.data.room.ContactConverter
import com.freephoenix888.savemylife.data.room.daos.ContactDao
import com.freephoenix888.savemylife.data.room.entities.ContactEntity
import com.freephoenix888.savemylife.data.room.entities.ContactPhoneNumberEntity

@Database(
    entities = [ContactEntity::class, ContactPhoneNumberEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(ContactConverter::class)
abstract class ContactDatabase: RoomDatabase() {

    abstract fun dao(): ContactDao
}