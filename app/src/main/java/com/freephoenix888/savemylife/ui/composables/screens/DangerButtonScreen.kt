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
fun AlarmButtonScreen(
    saveMyLifeViewModel: SaveMyLifeViewModel = viewModel(),
) {
    val alarmModeState by saveMyLifeViewModel.isAlarmModeEnabled.collectAsState(initial = false)
    AlarmButtonScreenBody(
        alarmModeState = alarmModeState,
        onSwitchIsAlarmModeEnabled = {
            saveMyLifeViewModel.switchIsAlarmModeEnabled()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AlarmButtonScreenBody(
    alarmModeState: Boolean,
    onSwitchIsAlarmModeEnabled: () -> Unit
) {
    Scaffold { innerPadding: PaddingValues ->
        Button(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(50.dp),
            onClick = onSwitchIsAlarmModeEnabled,
            colors = ButtonDefaults.buttonColors(containerColor = if(alarmModeState) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text(stringResource(R.string.all_i_am_in_alarm))
        }
    }
}


@Preview(showBackground = true, widthDp = 300, heightDp = 300)
@Composable
private fun AlarmButtonScreenBodyPreview() {
    var alarmModeState by remember { mutableStateOf(false)}
    AlarmButtonScreenBody(
        alarmModeState = alarmModeState,
        onSwitchIsAlarmModeEnabled = {
        alarmModeState = !alarmModeState
    })
}