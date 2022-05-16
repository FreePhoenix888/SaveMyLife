package com.freephoenix888.savemylife.data.storage.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact")
data class ContactEntity(
    @PrimaryKey @ColumnInfo(name = "uri") val uri: String,
) {

}