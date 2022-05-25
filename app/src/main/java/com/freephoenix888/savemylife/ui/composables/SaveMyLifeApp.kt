package com.freephoenix888.savemylife.ui.composables

import android.content.Intent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.freephoenix888.savemylife.constants.ActionConstants
import com.freephoenix888.savemylife.data.models.Setting
import com.freephoenix888.savemylife.ui.SaveMyLifeScreen
import com.freephoenix888.savemylife.ui.theme.SaveMyLifeTheme
import com.freephoenix888.savemylife.ui.viewModels.ContactsViewModel

@Composable
fun SaveMyLifeApp(intent: Intent? = null) {
    SaveMyLifeTheme {
        val navController = rememberNavController()
        val contactsViewModel: ContactsViewModel = viewModel()
        if(intent?.action == ActionConstants.ShowEmergencyButtonScreen){
            navController.navigate(SaveMyLifeScreen.DangerButton.name)
        }
        Scaffold { innerPadding ->
            SaveMyLifeNavHost(
                navController = navController,
                viewModel = contactsViewModel,
                modifier = Modifier.padding(innerPadding)
        )
        }
    }
}

@Composable
private fun SaveMyLifeNavHost(
    navController: NavHostController,
    viewModel: ContactsViewModel,
//    contacts: List<ContactModel>,
//    onRemoveContact: (contact: ContactModel) -> Unit,
//    onRemoveContactPhoneNumber: (contact: ContactModel, phoneNumber: PhoneNumber) -> Unit,
//    messageTemplate: String,
//    onMessageTemplateChange: (String) -> Unit,
//    messageSendingInterval: String,
//    onMessageSendingIntervalChange: (String) -> Unit,
//    isLocationSharingEnabled: Boolean,
//    onIsLocationSharingEnabled: (Boolean) -> Unit,
    modifier: Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = SaveMyLifeScreen.Home.name,
        modifier = modifier,
    ) {
        composable(SaveMyLifeScreen.Home.name) {
            HomeScreenComposable(navController = navController, viewModel = viewModel)
        }
        composable(SaveMyLifeScreen.Settings.name) {
            SettingsScreenComposable(
                onSettingClick = {setting: Setting ->
                    navController.navigate(setting.screenName)
                }
            )
        }
//        composable(SaveMyLifeScreen.ContactsSettings.name) {
//            ContactsSettingsScreenComposable(
//                viewModel = viewModel
////                contacts = contacts,
////                onRemoveContact = onRemoveContact,
////                onRemoveContactPhoneNumber = onRemoveContactPhoneNumber
//            )
//        }
//        composable(SaveMyLifeScreen.MessagesSettings.name) {
//            MessageSettingsScreenComposable(
//                viewModel = viewModel
////                messageTemplate = messageTemplate,
////                onMessageTemplateChange = onMessageTemplateChange,
////                onMessageTemplateInfoButtonClick = { /*TODO*/ },
////                messageSendingInterval = messageSendingInterval,
////                onMessageSendingIntervalChange = onMessageSendingIntervalChange
//            )
//        }
//        composable(SaveMyLifeScreen.LocationSettings.name) {
//            LocationSettingsScreenComposable(
//                viewModel = viewModel
////                isLocationSharingEnabled = isLocationSharingEnabled,
////                onIsLocationSharingEnabledChange = onIsLocationSharingEnabled
//            )
//        }
    }
}

@Preview
@Composable
private fun SaveMyLifeAppPreview() {
    SaveMyLifeApp()
}