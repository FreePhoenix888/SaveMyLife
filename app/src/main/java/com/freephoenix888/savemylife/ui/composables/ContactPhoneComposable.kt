package com.freephoenix888.savemylife.ui.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.freephoenix888.savemylife.ui.states.ContactsItemUiState
import com.freephoenix888.savemylife.data.models.PhoneNumber
import com.freephoenix888.savemylife.ui.composables.DefaultValuesForPreviews.defaultContact
import com.freephoenix888.savemylife.ui.composables.DefaultValuesForPreviews.defaultOnRemovePhoneNumber

@Composable
fun ContactPhoneComposable(
    contact: ContactsItemUiState,
    phoneNumber: PhoneNumber,
    onRemovePhoneNumber: (contact: ContactsItemUiState, phoneNumber: PhoneNumber) -> Unit
) {
    Row {
        Text(phoneNumber)
        IconButton(onClick = {
            onRemovePhoneNumber(contact, phoneNumber)
        }) {
            Icon(
                imageVector = Icons.Filled.Remove,
                contentDescription = "Remove contact phone number"
            )
        }
    }
}

@Preview
@Composable
private fun ContactPhoneComposablePreview() {
    val context = LocalContext.current
    ContactPhoneComposable(
        contact = defaultContact,
        phoneNumber = defaultContact.phoneNumbers.first(),
        onRemovePhoneNumber = { contact, phoneNumber ->
            defaultOnRemovePhoneNumber(context, contact, phoneNumber)
        })
}