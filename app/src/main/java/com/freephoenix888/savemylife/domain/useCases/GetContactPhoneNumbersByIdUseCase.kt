package com.freephoenix888.savemylife.domain.useCases

import android.content.Context
import android.provider.ContactsContract
import com.freephoenix888.savemylife.data.models.PhoneNumber
import com.freephoenix888.savemylife.domain.useCases.interfaces.GetContactPhoneNumbersUseCase
import javax.inject.Inject

class GetContactPhoneNumbersByIdUseCase @Inject constructor(val context: Context) : GetContactPhoneNumbersUseCase {
    override operator fun invoke(id: String): List<PhoneNumber> {
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