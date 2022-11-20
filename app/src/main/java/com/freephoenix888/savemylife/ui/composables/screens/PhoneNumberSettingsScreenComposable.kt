package com.freephoenix888.savemylife.ui.composables.screens

import android.Manifest
import android.content.Intent
import android.provider.ContactsContract
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.freephoenix888.savemylife.R
import com.freephoenix888.savemylife.Utils
import com.freephoenix888.savemylife.ui.composables.PhoneNumber
import com.freephoenix888.savemylife.ui.composables.RequestPermission
import com.freephoenix888.savemylife.ui.viewModels.PhoneNumberSettingsViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PhoneNumbersScreen(
    phoneNumberSettingsViewModel: PhoneNumberSettingsViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val readContactsPermissionState = rememberPermissionState(permission = android.Manifest.permission.READ_CONTACTS)
    if(!readContactsPermissionState.status.isGranted) {
        RequestPermission(
            permissionState = readContactsPermissionState,
            permissionHumanReadableName = "Read contacts",
            description = stringResource(R.string.phone_numbers_settings_screen_read_contacts_permission_request)
        )
        return
    }

    val sendSmsPermissionsState = rememberPermissionState(permission = Manifest.permission.SEND_SMS)
    if (!sendSmsPermissionsState.status.isGranted) {
        RequestPermission(
            permissionState = sendSmsPermissionsState,
            permissionHumanReadableName = "Send sms",
            description = "Receive sms permission is used to use find message commands from your messages."
        )
        return
    }

    val context = LocalContext.current as AppCompatActivity
    val phoneNumbers by phoneNumberSettingsViewModel.phoneNumbers.collectAsState()


    val intent = Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI)
    val pickContactActivityLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { activityResult ->
            val contactUri = activityResult.data?.data ?: return@rememberLauncherForActivityResult
            val phoneNumber =
                Utils.getPhoneNumberByContentUri(contentUri = contactUri, context = context)
            phoneNumberSettingsViewModel.addPhoneNumber(phoneNumber = phoneNumber)
        })

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(
                        imageVector = Icons.Filled.Phone,
                        contentDescription = stringResource(R.string.all_phone_numbers)
                    )
                    Text(stringResource(R.string.phone_number_settings_screen_phone_numbers))
                }

            },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }, content = {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.all_back)
                        )
                    })
                })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                pickContactActivityLauncher.launch(intent)
            }) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(R.string.phone_number_settings_screen_add_phone_number)
                )
            }
        }) { innerPadding: PaddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(innerPadding)
                .padding(dimensionResource(R.dimen.settings_screen_padding))
                .fillMaxSize()
        ) {
            Text("Emergency contacts will receive a message when danger mode is activated")
            LazyColumn {
                items(
                    items = phoneNumbers,
                    key = { it.contentUri }
                ) { phoneNumber ->
                    PhoneNumber(
                        phoneNumber = phoneNumber,
                        onDeletePhoneNumber = {
                            phoneNumberSettingsViewModel.removePhoneNumber(phoneNumber = phoneNumber)
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }

}


