package com.freephoenix888.savemylife.ui.composables

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle

@Composable
fun TextFieldError(error: String) {
    Text(
        text = error,
        style = TextStyle(color = MaterialTheme.colorScheme.error.copy(alpha = 0.8f))
    )
}