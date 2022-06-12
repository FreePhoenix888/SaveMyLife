package com.freephoenix888.savemylife.domain.models

import android.net.Uri

data class PhoneNumber(
    val contentUri: Uri,
    val contactName: String,
    val contactPhotoThumbnailUri: Uri?,
    val phoneNumber: String
)
