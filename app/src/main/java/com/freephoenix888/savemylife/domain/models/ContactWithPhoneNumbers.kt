package com.freephoenix888.savemylife.domain.models

import com.freephoenix888.savemylife.PhoneNumber

data class ContactWithPhoneNumbers(
    val contact: Contact,
    val phoneNumbers: List<PhoneNumber>
)