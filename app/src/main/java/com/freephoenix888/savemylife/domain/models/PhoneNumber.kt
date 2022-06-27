package com.freephoenix888.savemylife.domain.models

import android.net.Uri

data class PhoneNumber(
    val contentUri: Uri,
    val contactName: String,
    val contactImageThumbnailUri: Uri?,
    val phoneNumber: String
)
