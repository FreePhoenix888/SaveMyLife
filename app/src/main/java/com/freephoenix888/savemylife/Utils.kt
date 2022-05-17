package com.freephoenix888.savemylife

import android.content.Context
import android.provider.ContactsContract
import android.util.Log
import androidx.core.net.toUri
import androidx.lifecycle.viewModelScope
import com.freephoenix888.savemylife.data.models.ContactModel
import com.freephoenix888.savemylife.ui.fragments.ContactsSettingsFragment
import com.freephoenix888.savemylife.ui.viewModels.ContactViewModel
import kotlinx.coroutines.launch

class Utils {
    companion object {
        suspend fun getContactsByUri(context: Context, uri: String): List<ContactModel>{
            val contestResolver = context.contentResolver
            val cursor = contestResolver.query(uri.toUri(), null, null, null, null)
                ?: throw Throwable("Contact with uri $uri does not exist.")
            val contacts = mutableListOf<ContactModel>()
            if(cursor.moveToFirst()){
                val idColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID)
                val contactId = cursor.getString(idColumnIndex)
                Log.d(ContactsSettingsFragment.TAG, "Contact id: $contactId")

                val displayNameColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                val name = cursor.getString(displayNameColumnIndex)
                Log.d(ContactsSettingsFragment.TAG, "Display name: $name")

                val thumbnailUriColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI)
                val thumbnailUri: String? = cursor.getString(thumbnailUriColumnIndex)
                Log.d(ContactsSettingsFragment.TAG, "Thumbnail uri: $thumbnailUri")

                val hasPhoneColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)
                val hasPhoneNumber = cursor.getString(hasPhoneColumnIndex).toInt() == 1
                if(hasPhoneNumber){
                    val cursor2 = contestResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                        null,
                        null
                    )
                        ?: throw Throwable("Contact does not have phone number.")

                    while (cursor2.moveToNext()){
                        val phoneNumberColumnIndex = cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                        val phoneNumber = cursor2.getString(phoneNumberColumnIndex)
                        val contact = ContactModel(uri = uri, name = name, phoneNumber = phoneNumber, thumbnailUri = thumbnailUri)
                        contacts.add(contact)
                    }

                    cursor2.close()
                }
            }
            cursor.close()
            return contacts
        }
    }
}