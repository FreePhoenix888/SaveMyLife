package com.freephoenix888.savemylife.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.freephoenix888.savemylife.PhoneNumber
import com.freephoenix888.savemylife.constants.EmergencyContactsConstants
import com.freephoenix888.savemylife.domain.models.Contact
import com.freephoenix888.savemylife.domain.models.ContactPhoneNumber

@Composable
fun ContactPhoneComposable(
    contact: Contact,
    phoneNumber: PhoneNumber,
    onRemovePhoneNumber: (contactPhoneNumber: ContactPhoneNumber) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Text(text = phoneNumber, style = MaterialTheme.typography.body2)
        IconButton(onClick = {
            onRemovePhoneNumber(
                ContactPhoneNumber(
                    contactUri = contact.uri,
                    phoneNumber = phoneNumber
                )
            )
        }) {
            Icon(
                imageVector = Icons.Filled.Remove,
                contentDescription = "Remove contact phone number"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ContactPhoneComposablePreview() {
    val context = LocalContext.current
    var contactWithPhoneNumbers by remember { mutableStateOf(EmergencyContactsConstants.fakeContactsWithPhoneNumbers.first())}
    ContactPhoneComposable(
        contact = contactWithPhoneNumbers.contact,
        phoneNumber = contactWithPhoneNumbers.phoneNumbers.first(),
        onRemovePhoneNumber = {
            contactWithPhoneNumbers = contactWithPhoneNumbers.copy(
                phoneNumbers = listOf()
            )
        })
}