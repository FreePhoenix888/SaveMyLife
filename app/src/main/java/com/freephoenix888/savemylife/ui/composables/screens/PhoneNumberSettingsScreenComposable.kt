package com.freephoenix888.savemylife.ui.composables.screens

import android.content.Intent
import android.provider.ContactsContract
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.freephoenix888.savemylife.Utils
import com.freephoenix888.savemylife.domain.models.PhoneNumber
import com.freephoenix888.savemylife.ui.composables.PhoneNumberComposable
import com.freephoenix888.savemylife.ui.composables.RequestPermissionComposable
import com.freephoenix888.savemylife.ui.viewModels.PhoneNumberViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PhoneNumbersScreenComposable(
    phoneNumberViewModel: PhoneNumberViewModel = viewModel()
) {
    val context = LocalContext.current as AppCompatActivity
    val readContactsPermissionState =
        rememberPermissionState(permission = android.Manifest.permission.READ_CONTACTS)
    if (readContactsPermissionState.status != PermissionStatus.Granted) {
        RequestPermissionComposable(
            context = context,
            permissionState = readContactsPermissionState,
            text = "SaveMyLife needs access to your contacts to save your emergency contacts."
        )
        return
    }
    val phoneNumbers by phoneNumberViewModel.contactPhoneNumberList.collectAsState(
        initial = listOf()
    )

    ContactsSettingsScreenBodyComposable(
        phoneNumbers = phoneNumbers,
        onAddPhoneNumber = { phoneNumber ->
            phoneNumberViewModel.insert(phoneNumber)
        },
        onDeletePhoneNumber = {
            phoneNumberViewModel.delete(it)
        },
    )
}

@Composable
private fun ContactsSettingsScreenBodyComposable(
    phoneNumbers: List<PhoneNumber>,
    onAddPhoneNumber: (PhoneNumber) -> Unit,
    onDeletePhoneNumber: (PhoneNumber) -> Unit,
) {
    val context = LocalContext.current
    val intent = Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI)
    val pickContactActivityLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { activityResult ->
            val contactUri = activityResult.data?.data ?: return@rememberLauncherForActivityResult
            val phoneNumber =
                Utils.getPhoneNumberByContentUri(contentUri = contactUri, context = context)
            onAddPhoneNumber(phoneNumber)
        })
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Icon(imageVector = Icons.Filled.Phone, contentDescription = "Phone numbers")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Phone numbers")
            })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                pickContactActivityLauncher.launch(intent)
            }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add contact")
            }
        }) { innerPadding: PaddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            LazyColumn {
                items(
                    items = phoneNumbers,
                    key = { it.contentUri }
                ) { phoneNumber ->
                    PhoneNumberComposable(
                        phoneNumber = phoneNumber,
                        onDeletePhoneNumber = onDeletePhoneNumber,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }

}

@Preview(showBackground = true, widthDp = 320, heightDp = 480)
@Composable
fun ContactsSettingsScreenBodyComposablePreview() {
//    val emergencyContactsWithPhoneNumbers =
//        remember { mutableStateListOf<ContactWithPhoneNumbers>(*ContactsConstants.fakeContactsWithPhoneNumbers.toTypedArray()) }
//
//    ContactsSettingsScreenBodyComposable(
//        contactWithPhoneNumbersList = emergencyContactsWithPhoneNumbers,
//        onDeleteContact = {
//            val emergencyContactWithPhoneNumbersToRemove =
//                emergencyContactsWithPhoneNumbers.first { emergencyContactWithPhoneNumbers ->
//                    emergencyContactWithPhoneNumbers.contact.uri == it.uri
//                }
//            emergencyContactsWithPhoneNumbers.remove(emergencyContactWithPhoneNumbersToRemove)
//        },
//        onDeleteContactPhoneNumber = { contactPhoneNumber: ContactPhoneNumber ->
//            val emergencyContactWithPhoneNumbersToDeleteIndex =
//                emergencyContactsWithPhoneNumbers.indexOfFirst { emergencyContactWithPhoneNumbers ->
//                    emergencyContactWithPhoneNumbers.contact.uri == contactPhoneNumber.contactUri
//                }
//            val updatedContactWithPhoneNumbers =
//                emergencyContactsWithPhoneNumbers.get(emergencyContactWithPhoneNumbersToDeleteIndex).copy(
//                    phoneNumbers = emergencyContactsWithPhoneNumbers.get(
//                        emergencyContactWithPhoneNumbersToDeleteIndex
//                    ).phoneNumbers.filter { phoneNumber ->
//                        phoneNumber != contactPhoneNumber.phoneNumber
//                    }
//                )
//            emergencyContactsWithPhoneNumbers.removeAt(emergencyContactWithPhoneNumbersToDeleteIndex)
//            emergencyContactsWithPhoneNumbers.add(updatedContactWithPhoneNumbers)
//        },
//        onAddContactWithPhoneNumbers = {
//            emergencyContactsWithPhoneNumbers.add(it)
//        }
//    )
}