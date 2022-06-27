package com.freephoenix888.savemylife.ui.composables

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle

@Composable
fun TextFieldError(error: String) {
    Text(
        text = error,
        style = TextStyle(color = MaterialTheme.colors.error.copy(alpha = 0.8f))
    )
}