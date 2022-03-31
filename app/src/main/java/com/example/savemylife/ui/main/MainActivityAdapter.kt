package com.example.savemylife.ui.main

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.savemylife.databinding.ListItemBinding

class MainActivityAdapter(private val context: Activity, private val arrayList: ArrayList<SosActionModel>) : RecyclerView.Adapter<MainActivityAdapter.ViewHolder>() {

    class ViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sosAction = arrayList[position]
       holder.binding.apply {
           sosActionImage.setImageResource(sosAction.iconResourceId)
           sosActionTitle.text = sosAction.title
           sosActionSwitch.isChecked = sosAction.state
           sosActionSettingsButton.setOnClickListener ( sosAction.settingsButtonOnClickListener )
       }
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

}