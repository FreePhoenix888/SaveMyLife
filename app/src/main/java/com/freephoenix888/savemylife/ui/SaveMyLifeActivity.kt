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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.freephoenix888.savemylife.enums.IntentAction
import com.freephoenix888.savemylife.navigation.NavigationDestination
import com.freephoenix888.savemylife.services.MainService
import com.freephoenix888.savemylife.ui.composables.SaveMyLifeApp
import com.freephoenix888.savemylife.ui.viewModels.*
import com.vmadalin.easypermissions.EasyPermissions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SaveMyLifeActivity : AppCompatActivity() {

    private val saveMyLifeViewModel: SaveMyLifeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        manageMainService()

        setContent {
            val navController = rememberNavController()
            SaveMyLifeApp(navController)
            handleIntentAction(navController)
        }
    }

    private fun manageMainService() {
        saveMyLifeViewModel.viewModelScope.launch {
            saveMyLifeViewModel.isMainServiceEnabled.collect { isMainServiceEnabled ->
                val mainServiceIntent = Intent(this@SaveMyLifeActivity, MainService::class.java)
                Log.d(null, "activity onCreate saveMyLifeViewModel.isMainServiceEnabled.collect")
                this@SaveMyLifeActivity.apply {
                    if (isMainServiceEnabled) {
                        startForegroundService(mainServiceIntent)
                    } else {
                        stopService(mainServiceIntent)
                    }
                }
            }
        }
    }

    private fun handleIntentAction(navController: NavController) {
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
