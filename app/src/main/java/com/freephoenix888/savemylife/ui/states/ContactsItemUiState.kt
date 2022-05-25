package com.freephoenix888.savemylife.ui.states

import com.freephoenix888.savemylife.data.models.PhoneNumber

data class ContactsItemUiState(
    val uri: String,
    val name: String,
    val phoneNumbers: List<PhoneNumber>,
    val thumbnailUri: String? = null
) {

}