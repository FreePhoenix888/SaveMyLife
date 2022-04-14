package com.freephoenix888.savemylife.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.freephoenix888.savemylife.data.db.entities.Contact

@Database(
    entities = [Contact::class],
    version = 1,
    exportSchema = false
)
abstract class ContactDatabase: RoomDatabase() {

    abstract fun dao(): ContactDao

    companion object{
        @Volatile
        private var INSTANCE: ContactDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = INSTANCE ?: synchronized(LOCK) {
            INSTANCE ?: createDatabase(context).also { INSTANCE = it }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            ContactDatabase::class.java,
            "contact"
        ).build()

    }


}