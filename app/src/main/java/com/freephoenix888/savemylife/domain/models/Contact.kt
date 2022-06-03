package com.freephoenix888.savemylife.domain.models

import com.freephoenix888.savemylife.data.models.PhoneNumber

data class Contact(
    val uri: String,
    val name: String,
    val phoneNumbers: List<PhoneNumber>,
    val thumbnailUri: String? = null
) {

}