package com.freephoenix888.savemylife.data.models

import androidx.room.Ignore

data class ContactModel(
    val uri: String,
    val name: String,
    val phoneNumber: String,
    val thumbnailUri: String? = null
) {

}