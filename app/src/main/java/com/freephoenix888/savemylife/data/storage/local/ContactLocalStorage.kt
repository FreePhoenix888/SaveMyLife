package com.freephoenix888.savemylife.data.storage.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.freephoenix888.savemylife.data.storage.daos.ContactDao
import com.freephoenix888.savemylife.data.storage.entities.ContactEntity

@Database(
    entities = [ContactEntity::class],
    version = 1,
    exportSchema = true
)
abstract class ContactLocalStorage: RoomDatabase() {

    abstract fun dao(): ContactDao
}