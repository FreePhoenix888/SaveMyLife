package com.freephoenix888.savemylife.ui.composables

import android.graphics.BitmapFactory
import android.provider.ContactsContract
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonRemove
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.freephoenix888.savemylife.constants.EmergencyContactsConstants
import com.freephoenix888.savemylife.domain.models.Contact
import com.freephoenix888.savemylife.domain.models.ContactPhoneNumber
import com.freephoenix888.savemylife.domain.models.ContactWithPhoneNumbers


@Composable
fun EmergencyContactComposable(
    contactWithPhoneNumbers: ContactWithPhoneNumbers,
    onRemoveEmergencyContact: (contact: Contact) -> Unit,
    onRemoveEmergencyContactPhoneNumber: (contactPhoneNumber: ContactPhoneNumber) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val contactImageBitmap = remember {
            val inputStream = ContactsContract.Contacts.openContactPhotoInputStream(
                context.contentResolver, contactWithPhoneNumbers.contact.uri
            )
            return@remember if(inputStream == null) {
                null
            } else {
                BitmapFactory.decodeStream(inputStream).asImageBitmap()
            }
    }
    Card(
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
        ),
        modifier = Modifier.padding(16.dp)
    ) {
        Row(modifier = modifier) {
            if (contactImageBitmap != null) {
                Image(
                    bitmap = contactImageBitmap, contentDescription = "Contact image",
                    modifier = Modifier
                        .weight(2f)
                        .size(80.dp)
                )
            } else {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Contact image",
                    modifier = Modifier
//                        .align(Alignment.CenterVertically)
                        .weight(1f)
                        .size(80.dp)
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(3f)
            ) {
                Text(
                    text = contactWithPhoneNumbers.contact.name,
                    style = MaterialTheme.typography.body1,
                )
                Card(
                    border = BorderStroke(
                        width = 1.dp,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
                    ),
                ) {
                    Column {
                        contactWithPhoneNumbers.phoneNumbers.forEach { phoneNumber ->
                            ContactPhoneComposable(
                                contact = contactWithPhoneNumbers.contact,
                                phoneNumber = phoneNumber,
                                onRemovePhoneNumber = onRemoveEmergencyContactPhoneNumber,
                                modifier = Modifier.fillMaxWidth(),
                            )
                        }

                    }
                }
            }
            IconButton(
                onClick = { onRemoveEmergencyContact(contactWithPhoneNumbers.contact) },
                modifier = Modifier
//                    .align(Alignment.CenterVertically)
                    .weight(1f)
                    .padding(top = 16.dp)

            ) {
                Icon(
                    imageVector = Icons.Filled.PersonRemove,
                    contentDescription = "Remove contact",
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun ContactComposablePreview() {
    var contact by remember { mutableStateOf<ContactWithPhoneNumbers>(EmergencyContactsConstants.fakeContactsWithPhoneNumbers.first()) }
    EmergencyContactComposable(
        contactWithPhoneNumbers = contact,
        onRemoveEmergencyContact = {

        },
        onRemoveEmergencyContactPhoneNumber = { contactPhoneNumberToDelete ->
            contact = contact.copy(
                phoneNumbers = contact.phoneNumbers.filter { phoneNumber ->
                    phoneNumber == contactPhoneNumberToDelete.phoneNumber
                }
            )
        }
    )
}