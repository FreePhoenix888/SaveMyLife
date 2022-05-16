package com.freephoenix888.savemylife.data.db.entities

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "contact")
data class ContactEntity(
    @PrimaryKey @ColumnInfo(name = "uri") val uri: String,
) {

}