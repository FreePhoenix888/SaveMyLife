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
import com.freephoenix888.savemylife.domain.models.Contact
import com.freephoenix888.savemylife.ui.composables.EmergencyContactComposable
import com.freephoenix888.savemylife.ui.composables.RequestPermissionComposable
import com.freephoenix888.savemylife.ui.viewModels.EmergencyContactViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState


const val REQUEST_CODE_READ_WRITE_CONTACTS = 1

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun EmergencyContactsSettingsScreenComposable(
    emergencyContactViewModel: EmergencyContactViewModel = viewModel()
) {
    val context = LocalContext.current as AppCompatActivity
    val readContactsPermissionState =
        rememberPermissionState(permission = android.Manifest.permission.READ_CONTACTS)
    if (readContactsPermissionState.status != PermissionStatus.Granted) {
        RequestPermissionComposable(context = context, permissionState = readContactsPermissionState, text = "SaveMyLife needs access to your contacts to save your emergency contacts.")
        return
    }
    val emergencyContacts by emergencyContactViewModel.emergencyContacts.collectAsState(initial = listOf())
    EmergencyContactsSettingsScreenBodyComposable(
        emergencyContacts = emergencyContacts,
        onRemoveEmergencyContact = {
            emergencyContactViewModel.deleteEmergencyContacts(listOf(it))
        },
        onRemoveEmergencyContactPhoneNumber = { emergencyContactToRemovePhoneNumber, phoneNumberToRemove ->
            val updatedEmergencyContact = emergencyContactToRemovePhoneNumber.copy(
                phoneNumbers = emergencyContactToRemovePhoneNumber.phoneNumbers.filter {
                    it != phoneNumberToRemove
                }
            )
            emergencyContactViewModel.updateEmergencyContacts(listOf(updatedEmergencyContact))
        },
        onAddEmergencyContact = {
            emergencyContactViewModel.insertEmergencyContacts(listOf(it))
        }
    )
}

@Composable
private fun EmergencyContactsSettingsScreenBodyComposable(
    emergencyContacts: List<Contact>,
    onAddEmergencyContact: (Contact) -> Unit,
    onRemoveEmergencyContact: (Contact) -> Unit,
    onRemoveEmergencyContactPhoneNumber: (Contact, String) -> Unit,
) {
    val context = LocalContext.current
    val pickContactActivityLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickContact(),
        onResult = { contactUri: Uri? ->
            if (contactUri == null) {
                return@rememberLauncherForActivityResult
            }
            val contact = Utils.getContactByUri(uri = contactUri, context = context)
            onAddEmergencyContact(contact)
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
                    key = { it.uri }
                ) { contact: Contact ->
                    EmergencyContactComposable(
                        contact = contact,
                        onRemoveEmergencyContact = onRemoveEmergencyContact,
                        onRemoveEmergencyContactPhoneNumber = onRemoveEmergencyContactPhoneNumber,
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
    val emergencyContacts = remember {
        mutableStateListOf(
            Contact(
                uri = "1",
                name = "Name",
                phoneNumbers = listOf("+1-111-111-1111", "+2-222-222-2222")
            ),
            Contact(
                uri = "2",
                name = "Name",
                phoneNumbers = listOf("+3-333-333-3333", "+4-444-444-4444")
            )
        )
    }

    EmergencyContactsSettingsScreenBodyComposable(
        emergencyContacts = emergencyContacts,
        onRemoveEmergencyContact = {
            emergencyContacts.remove(it)
        },
        onRemoveEmergencyContactPhoneNumber = { emergencyContactToRemovePhoneNumber, phoneNumberToRemove ->
            val updatedEmergencyContact = emergencyContactToRemovePhoneNumber.copy(
                phoneNumbers = emergencyContactToRemovePhoneNumber.phoneNumbers.filter {
                    it != phoneNumberToRemove
                }
            )
            emergencyContacts.remove(emergencyContactToRemovePhoneNumber)
            emergencyContacts.add(updatedEmergencyContact)
        },
        onAddEmergencyContact = {
            emergencyContacts.add(it)
        }
    )
}