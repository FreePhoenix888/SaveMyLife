package com.freephoenix888.savemylife.data.models

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact")
class Contact(@PrimaryKey val id: Int, val phoneNumber: String, val name: String, val thumbnailUri: Uri) {
}