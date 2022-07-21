package com.freephoenix888.savemylife.ui.composables

import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonRemove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.freephoenix888.savemylife.R
import com.freephoenix888.savemylife.constants.PhoneNumberConstants
import com.freephoenix888.savemylife.domain.models.PhoneNumber

@Composable
fun PhoneNumberComposable(
    phoneNumber: PhoneNumber,
    onDeletePhoneNumber: (PhoneNumber) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val contactImageBitmap = remember {
        phoneNumber.contactImageThumbnailUri?.let { contactImageThumbnailUri ->
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) {
                return@remember MediaStore.Images.Media.getBitmap(
                    context.contentResolver,
                    contactImageThumbnailUri
                ).asImageBitmap()
            } else {
                val source =
                    ImageDecoder.createSource(context.contentResolver, contactImageThumbnailUri)
                return@remember ImageDecoder.decodeBitmap(source).asImageBitmap()
            }
        }
    }
    Card(
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f)
        ),
        modifier = Modifier.padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (contactImageBitmap != null) {
                Image(
                    bitmap = contactImageBitmap, contentDescription = stringResource(R.string.phone_numbers_settings_screen_contact_image),
                    modifier = Modifier
                        .weight(2f)
                        .size(80.dp)
                )
            } else {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = null,
                    modifier = Modifier
                        .weight(2f)
                        .size(80.dp)
                )
            }
            Column(
                modifier = Modifier.weight(7f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = phoneNumber.contactName, style = MaterialTheme.typography.body1,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = phoneNumber.phoneNumber, style = MaterialTheme.typography.body1,
                )
            }
            IconButton(
                onClick = { onDeletePhoneNumber(phoneNumber) },
                modifier = Modifier
                    .weight(1f)

            ) {
                Icon(
                    imageVector = Icons.Filled.PersonRemove,
                    contentDescription = stringResource(R.string.phone_number_settings_screen_delete_phone_number),
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PhoneNumberComposablePreview() {
    val contact by remember { mutableStateOf(PhoneNumberConstants.FAKE_PHONE_NUMBERS) }
    val phoneNumber by remember { mutableStateOf(PhoneNumberConstants.FAKE_PHONE_NUMBERS[0]) }
    PhoneNumberComposable(
        phoneNumber = phoneNumber,
        onDeletePhoneNumber = {},
    )
}