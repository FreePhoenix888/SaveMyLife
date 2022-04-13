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
import com.freephoenix888.savemylife.constants.Constants
import com.freephoenix888.savemylife.databinding.ActivityMainBinding
import com.freephoenix888.savemylife.services.MainService
import com.google.android.material.appbar.MaterialToolbar


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController
        navigateToEmergencyButtonFragmentIfNeeded(intent)
        setupToolbar()
        runMainService()
    }

    private fun setupToolbar(){
        val toolbar = binding.toolbarMain.toolbarMain
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
            it.action = Constants.ACTION_START_SERVICE
            startService(it)
        }
    }

    private fun navigateToEmergencyButtonFragmentIfNeeded(intent: Intent?){
        if(intent?.action == Constants.ACTION_SHOW_EMERGENCY_BUTTON_FRAGMENT){
            val emergencyButtonDirection = NavGraphDirections.actionGlobalEmergencyButton()
            navController.navigate(emergencyButtonDirection)
        }
    }

    private fun GetSettingsButtonClickListeners(): List<View.OnClickListener> {
        val listeners = mutableListOf<View.OnClickListener>()
        val settingsButtonActivityClasses = listOf(SosSmsActionActivity::class.java, SosShareLocationActionActivity::class.java, SosSmsActionActivity::class.java, SosSmsActionActivity::class.java)
        for (settingsButtonActivityClass in settingsButtonActivityClasses){
            listeners.add { view: View? ->
                val intent = Intent(this, settingsButtonActivityClass)
                this.startActivity(intent)
            }
        }
        return listeners
    }

}