package com.freephoenix888.savemylife.domain.useCases

import androidx.core.net.toUri
import com.freephoenix888.savemylife.data.repositories.EmergencyContactRepository
import com.freephoenix888.savemylife.data.room.databases.entities.ContactEntity
import com.freephoenix888.savemylife.domain.models.Contact
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetEmergencyContactsFlowUseCase @Inject constructor(val repository: EmergencyContactRepository, val getContactByUriUseCase: GetContactByUriUseCase) {
    operator fun invoke(): Flow<List<Contact>>{

        val convertedFlow = repository.contacts.map { contacts: List<ContactEntity> ->
            return@map contacts.map { contactEntity: ContactEntity ->
                getContactByUriUseCase(uri = contactEntity.uri.toUri())
            }
        }
        return convertedFlow
    }
}