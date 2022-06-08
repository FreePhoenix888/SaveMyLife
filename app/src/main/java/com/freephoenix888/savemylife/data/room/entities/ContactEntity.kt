package com.freephoenix888.savemylife.data.room.entities

import android.net.Uri
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact")
data class ContactEntity(
    @PrimaryKey @NonNull @ColumnInfo(name = "uri") val uri: Uri,
    @ColumnInfo(name = "name") val name: String,
)