package com.freephoenix888.savemylife.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable

@Composable
fun TextFieldWithErorr(
    textField: @Composable () -> Unit,
    error: @Composable (() -> Unit)? = null,
) {
    Column() {
        textField()
        error?.invoke()
    }
}