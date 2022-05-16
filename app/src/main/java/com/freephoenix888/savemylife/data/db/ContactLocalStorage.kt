package com.freephoenix888.savemylife.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.freephoenix888.savemylife.data.db.daos.ContactDao
import com.freephoenix888.savemylife.data.db.entities.ContactEntity

@Database(
    entities = [ContactEntity::class],
    version = 1,
    exportSchema = true
)
abstract class ContactLocalStorage: RoomDatabase() {

    abstract fun dao(): ContactDao
}