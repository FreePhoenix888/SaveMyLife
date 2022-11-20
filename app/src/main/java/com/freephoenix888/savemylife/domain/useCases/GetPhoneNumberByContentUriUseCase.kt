package com.freephoenix888.savemylife.domain.useCases

import android.content.Context
import android.net.Uri
import android.provider.ContactsContract
import androidx.core.net.toUri
import com.freephoenix888.savemylife.domain.models.PhoneNumber

class GetPhoneNumberByContentUriUseCase {
    operator fun invoke(context: Context, contentUri: Uri): PhoneNumber {
        val contestResolver = context.contentResolver
        val cursor = contestResolver.query(
            contentUri,
            arrayOf(
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
            ),
            null,
            null,
            null
        )
            ?: throw Throwable("Phone number with content uri $contentUri does not exist.")
        if (cursor.moveToFirst()) {
            val phoneNumberColumnIndex =
                cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
            val phoneNumber = cursor.getString(phoneNumberColumnIndex)

            val contactPhotoThumbnailUriColumnIndex =
                cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI)
            val contactPhotoThumbnailUri =
                cursor.getString(contactPhotoThumbnailUriColumnIndex)?.toUri()

            val contactDisplayNameColumnIndex =
                cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val contactDisplayName = cursor.getString(contactDisplayNameColumnIndex)

            cursor.close()
            return PhoneNumber(
                phoneNumber = phoneNumber,
                contactName = contactDisplayName,
                contactImageThumbnailUri = contactPhotoThumbnailUri,
                contentUri = contentUri
            )
        } else throw Throwable("Phone number with content uri $contentUri does not exist.")
    }
}