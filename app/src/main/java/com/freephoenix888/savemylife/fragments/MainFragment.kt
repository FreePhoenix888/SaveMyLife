package com.freephoenix888.savemylife.fragments

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.freephoenix888.savemylife.constants.Constants
import com.freephoenix888.savemylife.databinding.FragmentMainBinding
import com.freephoenix888.savemylife.constants.PreferencesConstants

class MainFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.settingsButton.setOnClickListener(this)
        return binding.root
    }


    override fun onClick(view: View?) {
        if (view == null){
            return
        }
        when(view.id){
            binding.serviceEnablerButton.root.id -> serviceEnablerButtonOnClickListener.onClick(view)
            binding.settingsButton.id -> settingsButtonOnClickListener.onClick(view)
        }
    }

    private val settingsButtonOnClickListener = View.OnClickListener {
        val navDirection = MainFragmentDirections.actionMainFragmentToSettingsFragment()
        findNavController().navigate(navDirection)
    }

    private val serviceEnablerButtonOnClickListener = View.OnClickListener {
        val preferences = context?.getSharedPreferences(PreferencesConstants.PREFERENCES_FILE_PATH, MODE_PRIVATE) ?: return@OnClickListener
        val isMainServiceEnabled = preferences.getBoolean(PreferencesConstants.IS_MAIN_SERVICE_ENABLED, true)
        preferences.edit()
            .putBoolean(PreferencesConstants.IS_MAIN_SERVICE_ENABLED, !isMainServiceEnabled)
            .apply()
    }
}