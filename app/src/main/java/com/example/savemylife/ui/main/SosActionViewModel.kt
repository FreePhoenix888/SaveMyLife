package com.example.savemylife.ui.main

import androidx.lifecycle.ViewModel

class SosActionViewModel : ViewModel() {
    private var _isChecked = false
    val isChecked: Boolean
        get() = _isChecked

    public fun setIsChecked(boolean: Boolean){
        _isChecked = boolean
    }
}