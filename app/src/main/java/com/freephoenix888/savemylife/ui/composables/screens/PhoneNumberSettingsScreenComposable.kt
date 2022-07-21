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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.freephoenix888.savemylife.R
import com.freephoenix888.savemylife.Utils
import com.freephoenix888.savemylife.ui.PhoneNumberSettingsFormEvent
import com.freephoenix888.savemylife.ui.composables.PhoneNumberComposable
import com.freephoenix888.savemylife.ui.composables.RequestPermissionComposable
import com.freephoenix888.savemylife.ui.viewModels.PhoneNumberSettingsViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PhoneNumbersScreenComposable(
    phoneNumberSettingsViewModel: PhoneNumberSettingsViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current as AppCompatActivity
    val state by phoneNumberSettingsViewModel.state.collectAsState()
    val readContactsPermissionState =
        rememberPermissionState(permission = android.Manifest.permission.READ_CONTACTS)
    if (readContactsPermissionState.status != PermissionStatus.Granted) {
        RequestPermissionComposable(
            permissionState = readContactsPermissionState,
            text = stringResource(R.string.phone_numbers_settings_screen_read_contacts_permission_request)
        )
        return
    }

    val sendSmsPermissionState =
        rememberPermissionState(permission = android.Manifest.permission.SEND_SMS)
    if(sendSmsPermissionState.status != PermissionStatus.Granted) {
        RequestPermissionComposable(
            permissionState = sendSmsPermissionState,
            text = stringResource(R.string.phone_numbers_settings_screen_send_sms_permission_request)
        )
        return
    }
    val intent = Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI)
    val pickContactActivityLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { activityResult ->
            val contactUri = activityResult.data?.data ?: return@rememberLauncherForActivityResult
            val phoneNumber =
                Utils.getPhoneNumberByContentUri(contentUri = contactUri, context = context)
            phoneNumberSettingsViewModel.onEvent(PhoneNumberSettingsFormEvent.PhoneNumberAdded(phoneNumber))
        })

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Icon(
                    imageVector = Icons.Filled.Phone,
                    contentDescription = stringResource(R.string.all_phone_numbers)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(stringResource(R.string.phone_number_settings_screen_phone_numbers))
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
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            LazyColumn {
                items(
                    items = state.phoneNumberList,
                    key = { it.contentUri }
                ) { phoneNumber ->
                    PhoneNumberComposable(
                        phoneNumber = phoneNumber,
                        onDeletePhoneNumber = {
                            phoneNumberSettingsViewModel.onEvent(PhoneNumberSettingsFormEvent.PhoneNumberDeleted(phoneNumber))
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }

}


