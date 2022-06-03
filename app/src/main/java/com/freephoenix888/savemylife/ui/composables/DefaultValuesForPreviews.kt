package com.freephoenix888.savemylife.ui.composables

import android.content.Context
import android.widget.Toast
import com.freephoenix888.savemylife.domain.models.Contact
import com.freephoenix888.savemylife.data.models.PhoneNumber

object DefaultValuesForPreviews {
    val defaultContact = Contact(
        uri = "0",
        name = "Name",
        phoneNumbers = listOf("+7 777 777 7777", "+7 777 777 7778", "+7 777 777 7779"),
        thumbnailUri = null
    )
}