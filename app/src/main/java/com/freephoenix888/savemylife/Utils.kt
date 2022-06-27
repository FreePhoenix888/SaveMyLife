package com.freephoenix888.savemylife

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.ContactsContract
import android.provider.Settings
import android.telephony.SmsManager
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.freephoenix888.savemylife.domain.models.PhoneNumber


class Utils {
    companion object {
        fun openAppSettings(context: Context) {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", context.packageName, null)
            intent.data = uri
            ContextCompat.startActivity(context, intent, null)
        }

        fun getContactLookupUri(id: Long, lookupKey: String): Uri {
            return ContactsContract.Contacts.getLookupUri(id, lookupKey)
        }

//        fun getContactByLookupUri(context: Context, lookupUri: Uri): Contact {
//            val contestResolver = context.contentResolver
//            val cursor = contestResolver.query(lookupUri, arrayOf(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI), null, null, null)
//                ?: throw Throwable("Contact with lookup uri $lookupUri does not exist.")
//            if (cursor.moveToFirst()) {
//                val displayNameColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
//                val displayName = cursor.getString(displayNameColumnIndex)
//
//                val photoThumbnailUriColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI)
//                val photoThumbnailUri = cursor.getString(photoThumbnailUriColumnIndex)
//
//                val contactLookupKeyColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY)
//                val contactLookupKey = cursor.getString(contactLookupKeyColumnIndex)
//
//                val contactIdColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
//                val contactId = cursor.getLong(contactIdColumnIndex)
//
//                cursor.close()
//                return Contact(
//                    lookupUri = getContactLookupUri(contactId, contactLookupKey),
//                    displayName = displayName,
//                    photoThumbnailUri = photoThumbnailUri?.toUri(),
//                )
//            } else throw Throwable("Contact with lookup uri $lookupUri does not exist.")
//        }
        
        fun getPhoneNumberByContentUri(context: Context, contentUri: Uri): PhoneNumber {
            val contestResolver = context.contentResolver
            val cursor = contestResolver.query(contentUri, arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME), null, null, null)
                ?: throw Throwable("Phone number with content uri $contentUri does not exist.")
            if (cursor.moveToFirst()) {
                val phoneNumberColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                val phoneNumber = cursor.getString(phoneNumberColumnIndex)

                val contactPhotoThumbnailUriColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI)
                val contactPhotoThumbnailUri = cursor.getString(contactPhotoThumbnailUriColumnIndex)?.toUri()

                val contactDisplayNameColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
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

        fun sendSms(context: Context, phoneNumber: String, message: String) {
            val flags = if(Build.VERSION.SDK_INT >= 23) PendingIntent.FLAG_IMMUTABLE else 0
            val sentPI: PendingIntent = PendingIntent.getBroadcast(context, 0, Intent("SMS_SENT"), flags)
            @Suppress("DEPRECATION") val smsManager: SmsManager =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    context.getSystemService(SmsManager::class.java)
                } else {
                    SmsManager.getDefault()
                }
            smsManager.sendTextMessage(phoneNumber, null, message, sentPI, null)
        }

    }
}