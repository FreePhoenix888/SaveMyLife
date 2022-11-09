package com.freephoenix888.savemylife.ui.composables.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.PowerSettingsNew
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.freephoenix888.savemylife.ui.viewModels.SaveMyLifeViewModel
import kotlin.math.roundToInt
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.freephoenix888.savemylife.R
import com.freephoenix888.savemylife.ui.SaveMyLifeScreenEnum


@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class,
    ExperimentalFoundationApi::class
)
@SuppressLint("BatteryLife")
@Composable
fun HomeScreen(
    navController: NavController,
    saveMyLifeViewModel: SaveMyLifeViewModel = viewModel()
) {
    val context = LocalContext.current
    val isMainServiceEnabled by saveMyLifeViewModel.isMainServiceEnabled.collectAsState(initial = false)
    val isFirstAppLaunch by saveMyLifeViewModel.isFirstAppLaunch.collectAsState(initial = false)

    var isSettingsHintDialogOpened by remember { mutableStateOf(isFirstAppLaunch) }
    if (isSettingsHintDialogOpened) {
        AlertDialog(onDismissRequest = { isSettingsHintDialogOpened = false }, title = {
            Text(stringResource(R.string.home_screen_switch_app_state))
        }, text = {
            Text(stringResource(R.string.home_screen_do_not_forget_to_adjust_settings_for_you_before_using_the_app))

        }, confirmButton = {
            Button(onClick = { isSettingsHintDialogOpened = false }) {
                Text(stringResource(R.string.all_ok))
            }
        })
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("SaveMyLife")
                },
                actions = {
                    IconButton(
                        onClick = {
                            navController.navigate(SaveMyLifeScreenEnum.Settings.name)
                        },
                        modifier = Modifier
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = stringResource(R.string.all_settings)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        ) {
            Button(
                onClick = {
                    saveMyLifeViewModel.switchIsMainServiceEnabled()
                },
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = if (isMainServiceEnabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error),
                modifier = Modifier
                    .size(150.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.PowerSettingsNew,
                    contentDescription = stringResource(R.string.home_screen_switch_app_state),
                    Modifier.fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = if (isMainServiceEnabled) stringResource(R.string.home_screen_app_state_enabled) else stringResource(
                    R.string.home_screen_app_state_disabled
                ),
            )

            Spacer(modifier = Modifier.height(50.dp))


            val width = 200.dp
            val squareSize = 48.dp

            val swipeableState = rememberSwipeableState(0) {
                Log.d(null, "Swipeable state (0 or 1): $it")
                return@rememberSwipeableState true
            }
            val swiperTrackWidthPx = with(LocalDensity.current) { width.toPx() }
            val swiperThumbWidthPx = with(LocalDensity.current) { squareSize.toPx() }
            val anchors = mapOf(
                0f to 0,
                swiperTrackWidthPx - swiperThumbWidthPx to 1
            ) // Maps anchor points (in px) to states

//            Box(
//                modifier = Modifier
//                    .width(width)
//                    .swipeable(
//                        state = swipeableState,
//                        anchors = anchors,
//                        thresholds = { _, _ -> FractionalThreshold(1f) },
//                        orientation = Orientation.Horizontal
//                    )
//                    .background(color = Color.LightGray, shape = RoundedCornerShape(50))
//            ) {
//                IconButton(
//                    modifier = Modifier
//                        .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
//                        .size(squareSize)
//                        .background(color = Color.DarkGray, shape = RoundedCornerShape(50)),
//            enabled = swipeableState.currentValue == 1,
//                    onClick = {
//
//                    },
//                    colors = IconButtonDefaults.iconButtonColors(contentColor = Color.Red)
//                ) {
//                    Icon(imageVector = Icons.Default.Campaign, contentDescription = "Enable alarm mode")
////                    Text("\uD83D\uDEA8", color = Color.Black)
//                }
//            }

            Surface(
                modifier = Modifier
                    .width(width)
                    .height(squareSize)
//                    .background(color = Color.LightGray, shape = RoundedCornerShape(50))
,
                color = Color.LightGray,
                contentColor = Color.Black,
//                contentAlignment = Alignment.Center
                shape = RoundedCornerShape(50),
//                color = MaterialTheme.colorScheme.primaryContainer,

            ) {
                Box(modifier = Modifier) {
                    Text(
                        "Alarm mode", textAlign = TextAlign.Center, modifier = Modifier.align(
                            Alignment.Center
                        )
                    )
                    Icon(imageVector = Icons.Default.Campaign,
                        contentDescription = "Enable alarm mode",
                        modifier = Modifier
                            .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
                            .size(squareSize)
                            .background(color = Color.DarkGray, shape = RoundedCornerShape(50))
                            .swipeable(
                                state = swipeableState,
                                anchors = anchors,
                                thresholds = { _, _ -> FractionalThreshold(1f) },
                                orientation = Orientation.Horizontal
                            ),
//                    tint = LocalContentColor.current
                        tint = MaterialTheme.colorScheme.primary.copy(
                            alpha = (swipeableState.offset.value / (swiperTrackWidthPx - swiperThumbWidthPx)).coerceIn(
                                0.5f,
                                1f
                            )
                        )
                    )

                }
            }


        }
    }

}