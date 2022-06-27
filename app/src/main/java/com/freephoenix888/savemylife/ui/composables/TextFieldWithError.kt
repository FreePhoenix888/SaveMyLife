package com.freephoenix888.savemylife.ui.composables

import androidx.compose.runtime.Composable

@Composable
fun TextFieldWithError(
    textFieldComposable: @Composable () -> Unit,
    error: String?
) {
    textFieldComposable()
    if (error != null) {
        TextFieldError(error)
    }
}