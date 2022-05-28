package com.freephoenix888.savemylife.domain.useCases

import android.content.Context
import android.provider.ContactsContract
import com.freephoenix888.savemylife.data.models.PhoneNumber
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetContactPhoneNumbersByIdUseCase @Inject constructor(@ApplicationContext val context: Context) {
    operator fun invoke(id: String): List<PhoneNumber> {
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
}