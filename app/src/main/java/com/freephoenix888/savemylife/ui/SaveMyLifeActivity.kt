package com.freephoenix888.savemylife.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.freephoenix888.savemylife.constants.ActionConstants
import com.freephoenix888.savemylife.services.MainService
import com.freephoenix888.savemylife.ui.composables.SaveMyLifeAppComposable
import com.freephoenix888.savemylife.ui.viewModels.LocationSettingsViewModel
import com.freephoenix888.savemylife.ui.viewModels.MessageSettingsViewModel
import com.freephoenix888.savemylife.ui.viewModels.PhoneNumberSettingsViewModel
import com.freephoenix888.savemylife.ui.viewModels.SaveMyLifeViewModel
import com.vmadalin.easypermissions.EasyPermissions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SaveMyLifeActivity : AppCompatActivity()  {

    companion object {
        private const val REQUEST_CODE_ACCESS_COARSE_LOCATION_PERMISSION = 1
    }

    private val saveMyLifeViewModel: SaveMyLifeViewModel by viewModels()
    private val phoneNumberSettingsViewModel: PhoneNumberSettingsViewModel by viewModels()
    private val emergencyMessageSettingsViewModel: MessageSettingsViewModel by viewModels()
    private val locationSettingsViewModel: LocationSettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        runMainService()
        setContent {
            SaveMyLifeAppComposable()
        }
    }

//    private fun setupToolbar(){
//        val toolbar = binding.toolbarMain.toolbar
//        val toolBarConfig = AppBarConfiguration(navController.graph)
//        setSupportActionBar(toolbar)
//        toolbar.setupWithNavController(navController, toolBarConfig)
//        supportActionBar?.setDisplayShowTitleEnabled(false)
//    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        navigateToButtonFragmentIfNeeded(intent)
    }

    private fun runMainService(){
        Intent(this, MainService::class.java).also {
            it.action = ActionConstants.StartMainService
            startService(it)
        }
    }

    private fun processIntentAction(intent: Intent?){
        navigateToButtonFragmentIfNeeded(intent)

    }

    private fun navigateToButtonFragmentIfNeeded(intent: Intent?){

    }
}