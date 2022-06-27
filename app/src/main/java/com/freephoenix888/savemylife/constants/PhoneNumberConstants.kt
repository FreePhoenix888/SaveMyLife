package com.freephoenix888.savemylife.constants

import androidx.core.net.toUri
import com.freephoenix888.savemylife.domain.models.PhoneNumber

object PhoneNumberConstants {

    val FAKE_PHONE_NUMBERS =
        listOf(
            PhoneNumber(
                phoneNumber = "+7 777 777 7779",
                contactName = "Name",
                contactImageThumbnailUri = null,
                contentUri = "0".toUri()
            ),
            PhoneNumber(
                phoneNumber = "+7 777 777 7778",
                contactName = "Name",
                contactImageThumbnailUri = null,
                contentUri = "0".toUri()
            ),
            PhoneNumber(
                phoneNumber = "+7 777 777 7797",
                contactName = "Name",
                contactImageThumbnailUri = null,
                contentUri = "1".toUri()
            ),
            PhoneNumber(
                phoneNumber = "+7 777 777 7787",
                contactName = "Name",
                contactImageThumbnailUri = null,
                contentUri = "1".toUri()
            ),
        )
}