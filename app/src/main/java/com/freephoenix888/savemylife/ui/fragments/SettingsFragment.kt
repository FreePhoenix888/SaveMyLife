package com.freephoenix888.savemylife.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.freephoenix888.savemylife.adapters.SettingsAdapter
import com.freephoenix888.savemylife.R
import com.freephoenix888.savemylife.data.models.Setting
import com.freephoenix888.savemylife.databinding.FragmentSettingsBinding
import androidx.recyclerview.widget.DividerItemDecoration


class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater)
        val settings = getSettings()
        binding.recyclerView.adapter = SettingsAdapter(requireContext(), settings)
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )
        return binding.root
    }

    private fun getSettings(): Array<Setting>{
        return arrayOf(
            Setting(R.drawable.ic_baseline_contacts_24, resources.getString(R.string.contacts)) {
                val direction = SettingsFragmentDirections.actionSettingsFragmentToContactsFragment()
                findNavController().navigate(direction)
            },
            Setting(R.drawable.ic_baseline_message_24, resources.getString(R.string.message)) {
                val direction = SettingsFragmentDirections.actionSettingsFragmentToMessageSettingsFragment()
                findNavController().navigate(direction)
            },
            Setting(R.drawable.ic_baseline_location_on_24, resources.getString(R.string.location)) {
                val direction = SettingsFragmentDirections.actionSettingsFragmentToLocationSettingsFragment()
                findNavController().navigate(direction)
            }
        )
    }

}