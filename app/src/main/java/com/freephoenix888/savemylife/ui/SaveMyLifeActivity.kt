package com.freephoenix888.savemylife.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.freephoenix888.savemylife.NavGraphDirections
import com.freephoenix888.savemylife.R
import com.freephoenix888.savemylife.SaveMyLifeApplication
import com.freephoenix888.savemylife.constants.ActionConstants
import com.freephoenix888.savemylife.databinding.ActivitySaveMyLifeBinding
import com.freephoenix888.savemylife.services.MainService
import com.freephoenix888.savemylife.ui.viewModels.ContactViewModel
import javax.inject.Inject


class SaveMyLifeActivity : AppCompatActivity()  {

    @Inject lateinit var viewModel: ContactViewModel
    private lateinit var binding: ActivitySaveMyLifeBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as SaveMyLifeApplication).appComponent.inject(this)
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
            it.action = ActionConstants.START_SERVICE
            startService(it)
        }
    }

    private fun navigateToEmergencyButtonFragmentIfNeeded(intent: Intent?){
        if(intent?.action == ActionConstants.SHOW_EMERGENCY_BUTTON_FRAGMENT){
            val emergencyButtonDirection = NavGraphDirections.actionGlobalEmergencyButton()
            navController.navigate(emergencyButtonDirection)
        }
    }

}