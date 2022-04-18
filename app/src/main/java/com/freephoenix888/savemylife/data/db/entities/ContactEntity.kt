package com.freephoenix888.savemylife.data.db.entities

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact")
data class ContactEntity(
    @ColumnInfo(name = "phone_number") val phoneNumber: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "thumbnail_uri") val thumbnailUri: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int? = null
}