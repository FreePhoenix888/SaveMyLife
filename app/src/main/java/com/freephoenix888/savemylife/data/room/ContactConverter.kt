package com.freephoenix888.savemylife.data.room

import android.net.Uri
import androidx.core.net.toUri
import androidx.room.TypeConverter

class ContactConverter {
    @TypeConverter
    fun fromUriToString(uri: Uri): String {
        return uri.toString()
    }

    @TypeConverter
    fun fromStringToUri(uri: String): Uri {
        return uri.toUri()
    }
}