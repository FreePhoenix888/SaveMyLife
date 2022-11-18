package com.freephoenix888.savemylife.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.viewModelScope
import com.freephoenix888.savemylife.R
import com.freephoenix888.savemylife.constants.ActionConstants
import com.freephoenix888.savemylife.constants.NotificationConstants
import com.freephoenix888.savemylife.constants.NotificationConstants.CHANNEL_ID
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

//        // Create an explicit intent for an Activity in your app
//        val intent = Intent(this, SaveMyLifeActivity::class.java).apply {
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        }
//        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
//
//        var builder = NotificationCompat.Builder(this, NotificationConstants.CHANNEL_ID)
//            .setSmallIcon(R.drawable.ic_stat_name)
//            .setContentTitle("Text")
//            .setContentText("Textrrr")
//            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//
//        // Create the NotificationChannel, but only on API 26+ because
//        // the NotificationChannel class is new and not in the support library
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val name = NotificationConstants.CHANNEL_NAME
//            val descriptionText = "Hi"
//            val importance = NotificationManager.IMPORTANCE_DEFAULT
//            val channel =
//                NotificationChannel(NotificationConstants.CHANNEL_ID, name, importance).apply {
//                    description = descriptionText
//                }
//            // Register the channel with the system
//            val notificationManager: NotificationManager =
//                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            notificationManager.createNotificationChannel(channel)
//        }
//
//        with(NotificationManagerCompat.from(this)) {
//            // notificationId is a unique int for each notification that you must define
//            notify(0, builder.build())
//        }



        saveMyLifeViewModel.viewModelScope.launch {
            saveMyLifeViewModel.isMainServiceEnabled.collect { isMainServiceEnabled ->
                Intent(this@SaveMyLifeActivity, MainService::class.java).also {
                    if (isMainServiceEnabled) {
                        it.action = ActionConstants.StartMainService
                        startService(it)
                    } else {
                        it.action = ActionConstants.StopMainService
                        stopService(it)
                    }
                }
            }
        }
        setContent {
            SaveMyLifeApp()
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

    private fun processIntentAction(intent: Intent?) {
        navigateToButtonFragmentIfNeeded(intent)

    }

    private fun navigateToButtonFragmentIfNeeded(intent: Intent?) {

    }
}