package com.freephoenix888.savemylife.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.freephoenix888.savemylife.data.models.Setting
import com.freephoenix888.savemylife.databinding.SettingBinding
import com.google.android.material.imageview.ShapeableImageView

class SettingsAdapter(
    private val context: Context,
    private val settings: Array<Setting>
): RecyclerView.Adapter<SettingsAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SettingBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(val binding: SettingBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val setting = settings[position]
        setIconImage(setting, holder.binding.icon)
        holder.binding.name.text = setting.name
        holder.binding.root.setOnClickListener(setting.onClickListener)
    }

    private fun setIconImage(setting: Setting, iconView: ShapeableImageView){
        val drawable = AppCompatResources.getDrawable(context, setting.iconDrawableId)
        iconView.setImageDrawable(drawable)
    }

    override fun getItemCount(): Int {
        return settings.size
    }
}