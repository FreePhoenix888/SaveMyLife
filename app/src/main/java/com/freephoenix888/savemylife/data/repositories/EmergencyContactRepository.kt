package com.freephoenix888.savemylife.data.repositories

import android.content.Context
import com.freephoenix888.savemylife.data.sources.interfaces.ContactLocalDataSource
import com.freephoenix888.savemylife.domain.models.Contact
import com.freephoenix888.savemylife.domain.models.ContactWithPhoneNumbers
import com.freephoenix888.savemylife.mappers.ContactToContactEntityMapper
import com.freephoenix888.savemylife.mappers.ContactWithPhoneNumbersRelationToContactWithPhoneNumbersMapper
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EmergencyContactRepository @Inject constructor(
    @ApplicationContext applicationContext: Context,
    private val localStorage: ContactLocalDataSource,
    private val contactToContactEntityMapper: ContactToContactEntityMapper,
    private val contactWithPhoneNumbersRelationToContactWithPhoneNumbersMapper: ContactWithPhoneNumbersRelationToContactWithPhoneNumbersMapper
) {

    suspend fun insert(contact: Contact) {
        return localStorage.insert(
            contactToContactEntityMapper.map(contact)
        )
    }

    suspend fun insertList(contacts: List<Contact>): List<Long> {
        return localStorage.insertList(contacts.map {
            contactToContactEntityMapper.map(it)
        })
    }

    suspend fun delete(contact: Contact) {
        return localStorage.delete(
            contactToContactEntityMapper.map(contact)
        )
    }

    suspend fun deleteList(contacts: List<Contact>): Int {
        return localStorage.deleteList(contacts.map {
            contactToContactEntityMapper.map(it)
        })
    }

    val contactsWithPhoneNumbers: Flow<List<ContactWithPhoneNumbers>> =
        localStorage.contactsWithPhoneNumbers.map { contactsWithPhoneNumbers ->
            contactsWithPhoneNumbers.map {
                contactWithPhoneNumbersRelationToContactWithPhoneNumbersMapper.map(it)
            }
        }
}
