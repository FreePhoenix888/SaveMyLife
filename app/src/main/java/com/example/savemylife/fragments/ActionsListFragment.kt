package com.example.savemylife.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.savemylife.R
import com.example.savemylife.SosShareLocationActionActivity
import com.example.savemylife.SosSmsActionActivity
import com.example.savemylife.databinding.FragmentSosActionsListBinding
import com.example.savemylife.ui.main.MainActivityAdapter
import com.example.savemylife.ui.main.SosActionModel

class ActionsListFragment : Fragment() {

    private lateinit var binding: FragmentSosActionsListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSosActionsListBinding.inflate(inflater, container, false)
        val recyclerView = binding.sosActionsList
        val sosActions = arrayListOf<SosActionModel>()
        val iconIds = resources.obtainTypedArray(R.array.sos_action_icon_ids)
        val titles = resources.getStringArray(R.array.sos_action_titles)
        val settingsButtonOnClickListeners: List<View.OnClickListener> = GetSettingsButtonClickListeners()
        for (i in 0 until iconIds.length()){
            val sosAction = SosActionModel(iconIds.getResourceId(i, 0), titles[i], false, settingsButtonOnClickListeners[i])
            sosActions.add(sosAction)
        }
        iconIds.recycle()
        recyclerView.adapter = MainActivityAdapter(sosActions)
        return binding.root
    }

    private fun GetSettingsButtonClickListeners(): List<View.OnClickListener> {
        val listeners = mutableListOf<View.OnClickListener>()
        val sosActionsSettingsLayouts = arrayOf(R.id.sosSmsActionSettingsFragment, R.id.sosSmsActionSettingsFragment, R.id.sosSmsActionSettingsFragment)
        for (sosActionSettingsLayout in sosActionsSettingsLayouts){
            listeners.add { view: View? ->
                findNavController().navigate(sosActionSettingsLayout)
            }
        }
        return listeners
    }

}