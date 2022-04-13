package com.freephoenix888.savemylife

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.freephoenix888.savemylife.constants.ActionConstants
import com.freephoenix888.savemylife.constants.Constants
import com.freephoenix888.savemylife.databinding.ActivitySaveMyLifeBinding
import com.freephoenix888.savemylife.services.MainService
import com.google.android.material.appbar.MaterialToolbar


class SaveMyLifeActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySaveMyLifeBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySaveMyLifeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        navigateToEmergencyButtonFragmentIfNeeded(intent)
        setupToolbar()
        runMainService()
    }

    private fun setupToolbar(){
        val toolbar = binding.toolbarMain.toolbar
        val toolBarConfig = AppBarConfiguration(navController.graph)
        setSupportActionBar(toolbar)
        toolbar.setupWithNavController(navController, toolBarConfig)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        navigateToEmergencyButtonFragmentIfNeeded(intent)
    }

    private fun runMainService(){
        Intent(this, MainService::class.java).also {
            it.action = ActionConstants.ACTION_START_SERVICE
            startService(it)
        }
    }

    private fun navigateToEmergencyButtonFragmentIfNeeded(intent: Intent?){
        if(intent?.action == ActionConstants.ACTION_SHOW_EMERGENCY_BUTTON_FRAGMENT){
            val emergencyButtonDirection = NavGraphDirections.actionGlobalEmergencyButton()
            navController.navigate(emergencyButtonDirection)
        }
    }

}