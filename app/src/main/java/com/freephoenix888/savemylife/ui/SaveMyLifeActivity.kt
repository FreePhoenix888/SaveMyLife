package com.freephoenix888.savemylife.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.freephoenix888.savemylife.constants.ActionConstants
import com.freephoenix888.savemylife.services.MainService
import com.freephoenix888.savemylife.ui.composables.SaveMyLifeApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SaveMyLifeActivity : AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SaveMyLifeApp(intent = intent)
        }
    }

//    private fun setupToolbar(){
//        val toolbar = binding.toolbarMain.toolbar
//        val toolBarConfig = AppBarConfiguration(navController.graph)
//        setSupportActionBar(toolbar)
//        toolbar.setupWithNavController(navController, toolBarConfig)
//        supportActionBar?.setDisplayShowTitleEnabled(false)
//    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        navigateToEmergencyButtonFragmentIfNeeded(intent)
    }

    private fun runMainService(){
        Intent(this, MainService::class.java).also {
            it.action = ActionConstants.StartMainService
            startService(it)
        }
    }

    private fun processIntentAction(intent: Intent?){
        navigateToEmergencyButtonFragmentIfNeeded(intent)

    }

    private fun navigateToEmergencyButtonFragmentIfNeeded(intent: Intent?){

    }
}