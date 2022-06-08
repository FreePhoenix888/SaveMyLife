package com.freephoenix888.savemylife

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.ContactsContract
import android.provider.Settings
import androidx.core.content.ContextCompat
import com.freephoenix888.savemylife.domain.models.Contact
import com.freephoenix888.savemylife.domain.models.ContactWithPhoneNumbers

class Utils {
    companion object {
        fun openAppSettings(context: Context) {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", context.packageName, null)
            intent.data = uri
            ContextCompat.startActivity(context, intent, null)
        }

        fun getContactPhoneNumbersById(context: Context, id: String): List<PhoneNumber>{
            val contestResolver = context.contentResolver
            val phoneNumbers = mutableListOf<PhoneNumber>()
            val cursor = contestResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                null,
                null
            )
                ?: return phoneNumbers

            while (cursor.moveToNext()) {
                val phoneNumberColumnIndex =
                    cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                val phoneNumber = cursor.getString(phoneNumberColumnIndex)
                phoneNumbers.add(phoneNumber)
            }

            cursor.close()
            return phoneNumbers
        }

        fun getContactWithPhoneNumbersByUri(context: Context, uri: Uri): ContactWithPhoneNumbers {
            val contestResolver = context.contentResolver
            val cursor = contestResolver.query(uri, null, null, null, null)
                ?: throw Throwable("Contact with uri $uri does not exist.")
            if (cursor.moveToFirst()) {
                val idColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID)
                val contactId = cursor.getString(idColumnIndex)

                val displayNameColumnIndex =
                    cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                val name = cursor.getString(displayNameColumnIndex)

                val thumbnailUriColumnIndex =
                    cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI)
                val thumbnailUri: String? = cursor.getString(thumbnailUriColumnIndex)

                val hasPhoneColumnIndex =
                    cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)
                val hasPhoneNumber = cursor.getString(hasPhoneColumnIndex).toInt() == 1
                var phoneNumbers = listOf<PhoneNumber>()
                if (hasPhoneNumber) {
                    phoneNumbers = getContactPhoneNumbersById(context = context, id = contactId)
                }

                cursor.close()

                return ContactWithPhoneNumbers(
                    contact = Contact(
                        uri = uri,
                        name = name,
                    ),
                    phoneNumbers = phoneNumbers
                )
            } else {
                throw Throwable("Contact with uri $uri does not exist.")
            }

        }
    }
}