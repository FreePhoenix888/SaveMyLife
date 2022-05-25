package com.freephoenix888.savemylife.ui.composables

import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonRemove
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.net.toUri
import com.freephoenix888.savemylife.ui.states.ContactsItemUiState
import com.freephoenix888.savemylife.data.models.PhoneNumber
import com.freephoenix888.savemylife.ui.composables.DefaultValuesForPreviews.defaultContact
import com.freephoenix888.savemylife.ui.composables.DefaultValuesForPreviews.defaultOnRemovePhoneNumber
import com.freephoenix888.savemylife.ui.composables.DefaultValuesForPreviews.defaultRemoveContact


@Composable
fun ContactComposable(
    contact: ContactsItemUiState,
    onRemoveContact: (contact: ContactsItemUiState) -> Unit,
    onRemoveContactPhoneNumber: (contact: ContactsItemUiState, phoneNumber: PhoneNumber) -> Unit
) {
    val context = LocalContext.current
    val contactImageBitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        ImageDecoder.decodeBitmap(
            ImageDecoder.createSource(
                context.contentResolver,
                contact.uri.toUri()
            )
        )
    } else {
        MediaStore.Images.Media.getBitmap(context.contentResolver, contact.uri.toUri())
    }
    Row {
        Icon(bitmap = contactImageBitmap.asImageBitmap(), contentDescription = "Contact image")
        Column {
            Text(contact.name)
            contact.phoneNumbers.forEach { phoneNumber ->
                ContactPhoneComposable(
                    contact = contact,
                    phoneNumber = phoneNumber,
                    onRemovePhoneNumber = onRemoveContactPhoneNumber
                )
            }
        }
        IconButton(onClick = { onRemoveContact(contact) }) {
            Icon(imageVector = Icons.Filled.PersonRemove, contentDescription = "Remove contact")
        }
    }
}

@Preview
@Composable
private fun ContactComposablePreview() {
    val context = LocalContext.current
    ContactComposable(
        contact = defaultContact,
        onRemoveContact = { contact ->
            defaultRemoveContact(context, contact)
        },
        onRemoveContactPhoneNumber = { contact, phoneNumber ->
            defaultOnRemovePhoneNumber(context, contact, phoneNumber)
        }
    )
}