package com.freephoenix888.savemylife.domain.models

import android.net.Uri
import com.freephoenix888.savemylife.PhoneNumber

data class ContactPhoneNumber(
    val contactUri: Uri,
    val phoneNumber: PhoneNumber
)
