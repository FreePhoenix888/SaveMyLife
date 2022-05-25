package com.freephoenix888.savemylife.domain.useCases

import android.content.Context
import android.net.Uri
import android.provider.ContactsContract
import android.util.Log
import com.freephoenix888.savemylife.ui.states.ContactsItemUiState
import com.freephoenix888.savemylife.data.models.PhoneNumber
import com.freephoenix888.savemylife.domain.useCases.interfaces.GetContactByUriUseCase
import com.freephoenix888.savemylife.domain.useCases.interfaces.GetContactPhoneNumbersUseCase

class GetLocalContactByUriUseCase(
    val context: Context,
    val getContactPhoneNumbersById: GetContactPhoneNumbersUseCase
) :
    GetContactByUriUseCase {
    private val TAG = this::class.simpleName
    override operator fun invoke(uri: Uri): ContactsItemUiState {
        val contestResolver = context.contentResolver
        val cursor = contestResolver.query(uri, null, null, null, null)
            ?: throw Throwable("Contact with uri $uri does not exist.")
        if (cursor.moveToFirst()) {
            val idColumnIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID)
            val contactId = cursor.getString(idColumnIndex)
            Log.d(TAG, "Contact id: $contactId")

            val displayNameColumnIndex =
                cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
            val name = cursor.getString(displayNameColumnIndex)
            Log.d(TAG, "Display name: $name")

            val thumbnailUriColumnIndex =
                cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI)
            val thumbnailUri: String? = cursor.getString(thumbnailUriColumnIndex)
            Log.d(TAG, "Thumbnail uri: $thumbnailUri")

            val hasPhoneColumnIndex =
                cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)
            val hasPhoneNumber = cursor.getString(hasPhoneColumnIndex).toInt() == 1
            var phoneNumbers = listOf<PhoneNumber>()
            if (hasPhoneNumber) {
                phoneNumbers = getContactPhoneNumbersById(id = contactId)
            }

            cursor.close()

            return ContactsItemUiState(
                uri = uri.toString(),
                name = name,
                phoneNumbers = phoneNumbers,
                thumbnailUri = thumbnailUri
            )
        } else {
            throw Throwable("Contact with uri $uri does not exist.")
        }
    }
}