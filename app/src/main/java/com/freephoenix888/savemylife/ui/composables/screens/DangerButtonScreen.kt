package com.freephoenix888.savemylife.ui.composables.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.freephoenix888.savemylife.R
import com.freephoenix888.savemylife.ui.viewModels.SaveMyLifeViewModel

@Composable
fun DangerButtonScreen(
    saveMyLifeViewModel: SaveMyLifeViewModel = viewModel(),
) {
    val dangerModeState by saveMyLifeViewModel.isDangerModeEnabled.collectAsState(initial = false)
    DangerButtonScreenBody(
        dangerModeState = dangerModeState,
        onSwitchIsDangerModeEnabled = {
            saveMyLifeViewModel.switchIsDangerModeEnabled()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DangerButtonScreenBody(
    dangerModeState: Boolean,
    onSwitchIsDangerModeEnabled: () -> Unit
) {
    Scaffold { innerPadding: PaddingValues ->
        Button(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(50.dp),
            onClick = onSwitchIsDangerModeEnabled,
            colors = ButtonDefaults.buttonColors(containerColor = if(dangerModeState) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text(stringResource(R.string.all_i_am_in_danger))
        }
    }
}


@Preview(showBackground = true, widthDp = 300, heightDp = 300)
@Composable
private fun DangerButtonScreenBodyPreview() {
    var dangerModeState by remember { mutableStateOf(false)}
    DangerButtonScreenBody(
        dangerModeState = dangerModeState,
        onSwitchIsDangerModeEnabled = {
        dangerModeState = !dangerModeState
    })
}