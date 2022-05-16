package com.freephoenix888.savemylife.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.freephoenix888.savemylife.databinding.FragmentLocationSettingsBinding
import com.freephoenix888.savemylife.constants.PreferencesConstants
import pub.devrel.easypermissions.EasyPermissions

class LocationSettingsFragment : Fragment(), View.OnClickListener {

    companion object {
        private val REQUEST_CODE_PERMISSION_FINE_LOCATION = 1
    }

    private lateinit var binding: FragmentLocationSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentLocationSettingsBinding.inflate(inflater)
        return binding.root
    }

    override fun onClick(view: View?) {
        if(view == null){
            return
        }
        when(view.id){
            binding.locationSharingSwitch.id -> locationSharingSwitchOnClickListener.onClick(view)
        }
    }

    private val locationSharingSwitchOnClickListener = View.OnClickListener {
        if(!EasyPermissions.hasPermissions(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)){
            EasyPermissions.requestPermissions(this, "Location permission must be granted to send location link in message.", REQUEST_CODE_PERMISSION_FINE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
        if(!EasyPermissions.hasPermissions(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)){
            EasyPermissions.requestPermissions(this, "Location permission must be granted to send location link in message.", REQUEST_CODE_PERMISSION_FINE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
        val preferences = requireContext().getSharedPreferences(
            PreferencesConstants.PREFERENCES_FILE_PATH,
            Context.MODE_PRIVATE
        )
        val isLocationSharingEnabled = preferences.getBoolean(PreferencesConstants.IS_LOCATION_SHARING_ENABLED, false)
        preferences.edit()
            .putBoolean(PreferencesConstants.IS_LOCATION_SHARING_ENABLED, !isLocationSharingEnabled)
            .apply()
    }

}