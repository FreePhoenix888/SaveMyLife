package com.freephoenix888.savemylife.data.room.entities

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.freephoenix888.savemylife.PhoneNumber

@Entity(tableName = "contact_phone_number")
data class ContactPhoneNumberEntity(
    @PrimaryKey @ColumnInfo(name = "phone_number") val phoneNumber: PhoneNumber,
    @ColumnInfo(name = "contact_uri") val contactUri: Uri
)
