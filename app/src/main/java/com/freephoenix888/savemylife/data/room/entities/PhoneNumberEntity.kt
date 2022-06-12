package com.freephoenix888.savemylife.data.room.entities

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "phone_number")
data class PhoneNumberEntity(
    @PrimaryKey @ColumnInfo(name = "content_uri") val contentUri: Uri,
)
