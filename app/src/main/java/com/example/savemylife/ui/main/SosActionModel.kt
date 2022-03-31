package com.example.savemylife.ui.main

import android.app.Activity
import android.view.View
import android.widget.ImageView

data class SosActionModel(val iconResourceId: Int, val title: String, val state: Boolean, val settingsButtonOnClickListener: View.OnClickListener) {

}