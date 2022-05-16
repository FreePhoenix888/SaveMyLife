package com.freephoenix888.savemylife.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.freephoenix888.savemylife.databinding.FragmentEmergencyButtonBinding

class EmergencyButtonFragment : Fragment() {

    private lateinit var binding: FragmentEmergencyButtonBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEmergencyButtonBinding.inflate(inflater, container, false)
        return binding.root
    }
}