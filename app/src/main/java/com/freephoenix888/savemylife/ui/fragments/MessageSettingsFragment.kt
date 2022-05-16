package com.freephoenix888.savemylife.ui.fragments

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import com.freephoenix888.savemylife.databinding.FragmentMessageSettingsBinding
import com.freephoenix888.savemylife.constants.PreferencesConstants
import pub.devrel.easypermissions.EasyPermissions


class MessageSettingsFragment : Fragment() {

    companion object {
        const val PERMISSION_REQUEST_CODE_SEND_SMS = 1
        const val PERMISSION_REQUEST_CODE_READ_SMS = 2
    }

    private lateinit var binding: FragmentMessageSettingsBinding
    private val preferences = requireContext().getSharedPreferences(PreferencesConstants.PREFERENCES_FILE_PATH, MODE_PRIVATE)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        checkAndRequestPermissions()
        binding = FragmentMessageSettingsBinding.inflate(inflater)
        binding.infoButtonMessageTemplate.doAfterTextChanged { text: Editable? ->
            preferences.edit()
                .putString(PreferencesConstants.MESSAGE_TEMPLATE, text.toString())
                .apply()
        }
        binding.inputMessageTemplate.setText(preferences.getString(PreferencesConstants.MESSAGE_TEMPLATE, null))
        binding.inputMessageSendingInterval.doAfterTextChanged { text: Editable? ->
            preferences.edit()
                .putString(PreferencesConstants.MESSAGE_SENDING_INTERVAL, text.toString())
                .apply()
        }
        binding.inputMessageSendingInterval.setText(preferences.getString(PreferencesConstants.MESSAGE_SENDING_INTERVAL, null))
        return binding.root
    }

    private fun checkAndRequestPermissions(){
        EasyPermissions.requestPermissions(
            this,
            "Send sms permission is required to send sms.",
            PERMISSION_REQUEST_CODE_SEND_SMS,
            android.Manifest.permission.SEND_SMS
        )
        EasyPermissions.requestPermissions(
            this,
            "Read sms permission is required to get sms commands from your emergency contact. For example they can ask to send your message again with actual information.",
            PERMISSION_REQUEST_CODE_SEND_SMS,
            android.Manifest.permission.SEND_SMS
        )
    }

}