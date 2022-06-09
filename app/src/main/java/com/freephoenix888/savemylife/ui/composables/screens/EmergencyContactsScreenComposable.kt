package com.freephoenix888.savemylife.ui.composables.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.freephoenix888.savemylife.Utils
import com.freephoenix888.savemylife.constants.EmergencyContactsConstants
import com.freephoenix888.savemylife.domain.models.Contact
import com.freephoenix888.savemylife.domain.models.ContactPhoneNumber
import com.freephoenix888.savemylife.domain.models.ContactWithPhoneNumbers
import com.freephoenix888.savemylife.ui.composables.EmergencyContactComposable
import com.freephoenix888.savemylife.ui.composables.RequestPermissionComposable
import com.freephoenix888.savemylife.ui.viewModels.EmergencyContactPhoneNumbersViewModel
import com.freephoenix888.savemylife.ui.viewModels.EmergencyContactViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState


const val REQUEST_CODE_READ_WRITE_CONTACTS = 1

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun EmergencyContactsSettingsScreenComposable(
    emergencyContactViewModel: EmergencyContactViewModel = viewModel(),
    emergencyContactPhoneNumbersViewModel: EmergencyContactPhoneNumbersViewModel = viewModel()
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
    val emergencyContacts by emergencyContactViewModel.emergencyContactsWithPhoneNumbers.collectAsState(
        initial = listOf()
    )
    EmergencyContactsSettingsScreenBodyComposable(
        emergencyContacts = emergencyContacts,
        onDeleteEmergencyContact = {
            emergencyContactViewModel.delete(it)
        },
        onDeleteEmergencyContactPhoneNumber = {
            emergencyContactPhoneNumbersViewModel.delete(it)
        },
        onAddContactWithPhoneNumbers = { contactWithPhoneNumbers ->
            emergencyContactViewModel.insert(contactWithPhoneNumbers.contact)
            emergencyContactPhoneNumbersViewModel.insertList(
                contactWithPhoneNumbers.phoneNumbers.map { phoneNumber ->
                    ContactPhoneNumber(
                        contactUri = contactWithPhoneNumbers.contact.uri,
                        phoneNumber = phoneNumber
                    )
                }
            )
        },
    )
}

@Composable
private fun EmergencyContactsSettingsScreenBodyComposable(
    emergencyContacts: List<ContactWithPhoneNumbers>,
    onAddContactWithPhoneNumbers: (ContactWithPhoneNumbers) -> Unit,
    onDeleteEmergencyContact: (Contact) -> Unit,
    onDeleteEmergencyContactPhoneNumber: (ContactPhoneNumber) -> Unit,
) {
    val context = LocalContext.current
    val pickContactActivityLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickContact(),
        onResult = { contactUri: Uri? ->
            if (contactUri == null) {
                return@rememberLauncherForActivityResult
            }
            val contactWithPhoneNumbers =
                Utils.getContactWithPhoneNumbersByUri(uri = contactUri, context = context)
            onAddContactWithPhoneNumbers(contactWithPhoneNumbers)
        })
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text("Emergency contacts")
            })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                pickContactActivityLauncher.launch()
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
                    items = emergencyContacts,
                    key = { it.contact.uri }
                ) { contact: ContactWithPhoneNumbers ->
                    EmergencyContactComposable(
                        contactWithPhoneNumbers = contact,
                        onRemoveEmergencyContact = onDeleteEmergencyContact,
                        onRemoveEmergencyContactPhoneNumber = onDeleteEmergencyContactPhoneNumber,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }

}

@Preview(showBackground = true, widthDp = 320, heightDp = 480)
@Composable
fun EmergencyContactsSettingsScreenBodyComposablePreview() {
    val emergencyContactsWithPhoneNumbers =
        remember { mutableStateListOf<ContactWithPhoneNumbers>(*EmergencyContactsConstants.fakeContactsWithPhoneNumbers.toTypedArray()) }

    EmergencyContactsSettingsScreenBodyComposable(
        emergencyContacts = emergencyContactsWithPhoneNumbers,
        onDeleteEmergencyContact = {
            val emergencyContactWithPhoneNumbersToRemove =
                emergencyContactsWithPhoneNumbers.first { emergencyContactWithPhoneNumbers ->
                    emergencyContactWithPhoneNumbers.contact.uri == it.uri
                }
            emergencyContactsWithPhoneNumbers.remove(emergencyContactWithPhoneNumbersToRemove)
        },
        onDeleteEmergencyContactPhoneNumber = { contactPhoneNumber: ContactPhoneNumber ->
            val emergencyContactWithPhoneNumbersToDeleteIndex =
                emergencyContactsWithPhoneNumbers.indexOfFirst { emergencyContactWithPhoneNumbers ->
                    emergencyContactWithPhoneNumbers.contact.uri == contactPhoneNumber.contactUri
                }
            val updatedEmergencyContactWithPhoneNumbers =
                emergencyContactsWithPhoneNumbers.get(emergencyContactWithPhoneNumbersToDeleteIndex).copy(
                    phoneNumbers = emergencyContactsWithPhoneNumbers.get(
                        emergencyContactWithPhoneNumbersToDeleteIndex
                    ).phoneNumbers.filter { phoneNumber ->
                        phoneNumber != contactPhoneNumber.phoneNumber
                    }
                )
            emergencyContactsWithPhoneNumbers.removeAt(emergencyContactWithPhoneNumbersToDeleteIndex)
            emergencyContactsWithPhoneNumbers.add(updatedEmergencyContactWithPhoneNumbers)
        },
        onAddContactWithPhoneNumbers = {
            emergencyContactsWithPhoneNumbers.add(it)
        }
    )
}