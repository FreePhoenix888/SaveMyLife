package com.freephoenix888.savemylife.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.rememberNavController
import com.freephoenix888.savemylife.enums.IntentAction
import com.freephoenix888.savemylife.navigation.NavigationDestination
import com.freephoenix888.savemylife.services.MainService
import com.freephoenix888.savemylife.ui.composables.SaveMyLifeApp
import com.freephoenix888.savemylife.ui.viewModels.LocationSharingSettingsViewModel
import com.freephoenix888.savemylife.ui.viewModels.MessageSettingsViewModel
import com.freephoenix888.savemylife.ui.viewModels.PhoneNumberSettingsViewModel
import com.freephoenix888.savemylife.ui.viewModels.SaveMyLifeViewModel
import com.vmadalin.easypermissions.EasyPermissions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SaveMyLifeActivity : AppCompatActivity() {

    companion object {
        private const val REQUEST_CODE_ACCESS_COARSE_LOCATION_PERMISSION = 1
    }

    private val saveMyLifeViewModel: SaveMyLifeViewModel by viewModels()
    private val phoneNumberSettingsViewModel: PhoneNumberSettingsViewModel by viewModels()
    private val emergencyMessageSettingsViewModel: MessageSettingsViewModel by viewModels()
    private val locationSharingSettingsViewModel: LocationSharingSettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        saveMyLifeViewModel.viewModelScope.launch {
            saveMyLifeViewModel.isMainServiceEnabled.collect { isMainServiceEnabled ->
                Intent(this@SaveMyLifeActivity, MainService::class.java).also { mainServiceIntent ->
                    Log.d(null, "activity onCreate  saveMyLifeViewModel.isMainServiceEnabled.collect")
                    if(isMainServiceEnabled){
                        if(Build.VERSION.SDK_INT >= 26) {
                            this@SaveMyLifeActivity.startForegroundService(mainServiceIntent)
                        } else {
                            this@SaveMyLifeActivity.startService(mainServiceIntent)
                        }

                    } else {
                        this@SaveMyLifeActivity.stopService(mainServiceIntent)
                    }
                }
            }
        }

        setContent {
            val navController = rememberNavController()
            SaveMyLifeApp(navController)
            when (intent.action) {
                IntentAction.EnableDangerMode.name -> {
                    if (Build.VERSION.SDK_INT >= 27) {
                        setShowWhenLocked(true)
                        setTurnScreenOn(true)
                    } else {
                        @Suppress("DEPRECATION")
                        window.addFlags(
                            WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or
                                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                        )
                    }
                    navController.navigate(NavigationDestination.DangerModeActivationConfirmation.name)
                }
                else -> {}
            }
        }


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}