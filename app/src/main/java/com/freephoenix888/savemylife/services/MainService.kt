package com.freephoenix888.savemylife.services

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Build
import android.os.CombinedVibration
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.provider.Settings
import android.provider.Telephony
import android.util.Log
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.*
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.*
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.freephoenix888.savemylife.MyLifecycleOwner
import com.freephoenix888.savemylife.R
import com.freephoenix888.savemylife.broadcastReceivers.AlarmBroadcastReceiver
import com.freephoenix888.savemylife.broadcastReceivers.PowerButtonBroadcastReceiver
import com.freephoenix888.savemylife.broadcastReceivers.SmsBroadcastReceiver
import com.freephoenix888.savemylife.constants.MessageConstants
import com.freephoenix888.savemylife.constants.NotificationConstants
import com.freephoenix888.savemylife.constants.NotificationConstants.CHANNEL_ID
import com.freephoenix888.savemylife.domain.models.PhoneNumber
import com.freephoenix888.savemylife.domain.useCases.*
import com.freephoenix888.savemylife.ui.SaveMyLifeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import javax.inject.Inject


@AndroidEntryPoint
class MainService : LifecycleService() {

    companion object{
        private val TAG = MainService::class.java.simpleName
    }

    @Inject
    lateinit var getMessageSettingsFlowUseCase: GetMessageSettingsFlowUseCase

    @Inject
    lateinit var getIsMainServiceEnabledFlowUseCase: GetIsMainServiceEnabledFlowUseCase

    @Inject
    lateinit var getIsAlarmModeEnabledFlowUseCase: GetIsAlarmModeEnabledFlowUseCase

    @Inject
    lateinit var setIsAlarmModeEnabledFlowUseCase: SetIsAlarmModeEnabledFlowUseCase

    @Inject
    lateinit var getPhoneNumberListFlowUseCase: GetPhoneNumberListFlowUseCase

    @Inject
    lateinit var getMessageUseCase: GetMessageUseCase

    private var smsBroadcastReceiver: SmsBroadcastReceiver? = null
    private var isFirstStart = true
    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val isAlarmModeEnabled = MutableStateFlow(false)
    private val isMessageCommandsEnabled = MutableStateFlow(false)
    private val messageTemplate = MutableStateFlow("")
    private val messageSendingInterval = MutableStateFlow(MessageConstants.DEFAULT_SENDING_INTERVAL)
    private val phoneNumberList = MutableStateFlow(emptyList<PhoneNumber>())
    private val powerButtonBroadcastReceiver = PowerButtonBroadcastReceiver()

    private var alarmManager: AlarmManager? = null
    private var alarmIntent: PendingIntent? = null

    private val alarmModeBeforeStartTimerInSeconds = MutableStateFlow(5)

    private val windowManager by lazy {
        applicationContext.getSystemService(Context.WINDOW_SERVICE)
                as WindowManager
    }

    private val alarmModeEnsuringView by lazy {
        ComposeView(this)
    }


    override fun onCreate() {
        startForegroundService()
        registerPowerButtonBroadcastReceiver()
        super.onCreate()
    }

    init {
        lifecycleScope.launchWhenCreated {
            withContext(Dispatchers.IO) {
                getIsAlarmModeEnabledFlowUseCase().collect() {
                    Log.d(TAG, "getIsAlarmModeEnabledFlowUseCase: start $it")
                    alarmManager =
                        applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                    if (alarmIntent != null) {
                        alarmManager?.cancel(alarmIntent)
                    }
                    if(!it) {
                        isAlarmModeEnabled.value = it
                        return@collect
                    }
                    withContext(Dispatchers.Main) {
                        showOverlay()
                    }
                    while(alarmModeBeforeStartTimerInSeconds.value != 0) {
                        delay(1000)
                        alarmModeBeforeStartTimerInSeconds.value = alarmModeBeforeStartTimerInSeconds.value - 1
                    }
                    if(alarmModeEnsuringView.windowToken != null) {
                        windowManager.removeView(alarmModeEnsuringView)
                    }

                    alarmModeBeforeStartTimerInSeconds.value = 5

                    if(!getIsAlarmModeEnabledFlowUseCase().first()) {
                        return@collect
                    }

                    val dialogIntent =
                        Intent(applicationContext, AlarmBroadcastReceiver::class.java)
                    alarmIntent = PendingIntent.getBroadcast(
                        applicationContext,
                        0,
                        dialogIntent,
                        PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_CANCEL_CURRENT
                    )
                    alarmManager?.setRepeating(
                        AlarmManager.RTC_WAKEUP,
                        System.currentTimeMillis(),
                        messageSendingInterval.value.inWholeMilliseconds,
                        alarmIntent
                    )
                }
            }
        }
        lifecycleScope.launchWhenCreated {
            withContext(Dispatchers.IO) {
                getIsMainServiceEnabledFlowUseCase().collect {
                    if (!it) {
                        applicationContext.stopService(
                            Intent(
                                applicationContext,
                                MainService::class.java
                            )
                        )
                    }
                }
            }

        }
        lifecycleScope.launchWhenCreated {
            withContext(Dispatchers.IO) {
                getPhoneNumberListFlowUseCase().collect {
                    phoneNumberList.value = it
                }
            }
        }
        lifecycleScope.launchWhenCreated {
            withContext(Dispatchers.IO) {
                getMessageSettingsFlowUseCase().collect {
                    messageSendingInterval.value = it.sendingInterval
                    isMessageCommandsEnabled.value = it.isMessageCommandsEnabled
                    messageTemplate.value = it.template

                    if(it.isMessageCommandsEnabled && smsBroadcastReceiver != null){
                        smsBroadcastReceiver = SmsBroadcastReceiver()
                        val filter = IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)
                        registerReceiver(smsBroadcastReceiver, filter, 2)
                    } else if (smsBroadcastReceiver != null) {
                        unregisterReceiver(smsBroadcastReceiver)
                    }
                }

            }
        }
        lifecycleScope.launchWhenCreated {
            withContext(Dispatchers.IO) {
                alarmModeBeforeStartTimerInSeconds.collect {
                    Log.d(TAG, "NEW TIMER VALUE: $it")
                }
            }
        }

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        super.onStartCommand(intent, flags, startId)
        registerPowerButtonBroadcastReceiver()
        if (intent == null) {
            return START_STICKY
        }

        when (intent.action) {
            ActionConstants.StartMainService -> {
                if (isFirstStart) {
                    startForegroundService()
                    isFirstStart = false
                }
            }
            else -> {}
        }
        return START_STICKY
    }

    override fun onDestroy() {
        sendBroadcast(Intent(applicationContext, RestartBroadcastReceiver::class.java))
        unregisterReceiver(powerButtonBroadcastReceiver)
        if(smsBroadcastReceiver != null) {
            unregisterReceiver(smsBroadcastReceiver)
        }
        super.onDestroy()
    }

    private fun registerPowerButtonBroadcastReceiver() {
        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_SCREEN_ON)
            addAction(Intent.ACTION_SCREEN_OFF)
        }
        registerReceiver(powerButtonBroadcastReceiver, filter)
    }

    private fun startForegroundService() {
        val notification = getForegroundServiceNotification()
        startForeground(NotificationConstants.ID, notification)
    }

    private fun getForegroundServiceNotification(): Notification {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }
        val saveMyLifeActivityIntent = Intent(this, SaveMyLifeActivity::class.java)/*.apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }*/
        val saveMyLifeActivityPendingIntent = PendingIntent.getActivity(
            this, 0, saveMyLifeActivityIntent,
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
        )
        val notificationBuilder = NotificationCompat.Builder(this, NotificationConstants.CHANNEL_ID)
            .setAutoCancel(false)
            .setOngoing(true)
            .setSmallIcon(R.drawable.ic_stat_name)
            .setLargeIcon(
                BitmapFactory.decodeResource(applicationContext.resources, R.mipmap.ic_launcher)
            )
            .setContentTitle("SaveMyLife")
            .setContentText("SaveMyLife is active")
            .setContentIntent(saveMyLifeActivityPendingIntent)
        return notificationBuilder.build()
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.all_app_name)
            val descriptionText = getString(R.string.all_app_name)
            val importance = NotificationManager.IMPORTANCE_MAX
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun showOverlay() {
        if (!Settings.canDrawOverlays(this)) {
            // Create an explicit intent for an Activity in your app
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            ).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
//            val intent = Intent(applicationContext, SaveMyLifeActivity::class.java).apply {
//                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            }
            val pendingIntent: PendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_IMMUTABLE)

            var builder = NotificationCompat.Builder(applicationContext, NotificationConstants.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle("Provide ")
                .setContentText("Textrrr")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)

            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = NotificationConstants.CHANNEL_NAME
                val descriptionText = "Hi"
                val importance = NotificationManager.IMPORTANCE_HIGH
                val channel =
                    NotificationChannel(NotificationConstants.CHANNEL_ID, name, importance).apply {
                        description = descriptionText
                    }
                // Register the channel with the system
                val notificationManager: NotificationManager =
                    applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }

            with(NotificationManagerCompat.from(applicationContext)) {
                // notificationId is a unique int for each notification that you must define
                notify(125125, builder.build())
            }
            return
        }

        val layoutFlag: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
        } else {
            WindowManager.LayoutParams.TYPE_PHONE
        }

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT,
            layoutFlag,
            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or WindowManager.LayoutParams.FLAG_SPLIT_TOUCH or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS /*focus*/,
            PixelFormat.TRANSLUCENT
        )

        alarmModeEnsuringView.setContent {
            val alarmModeBeforeStartTimerInSeconds = alarmModeBeforeStartTimerInSeconds.collectAsState()
            Column(modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text("Alarm mode will be enabled in ${alarmModeBeforeStartTimerInSeconds.value}")
                Button(onClick = {
                    lifecycleScope.launch(Dispatchers.IO) {
                        setIsAlarmModeEnabledFlowUseCase(false)
                        if(alarmModeEnsuringView.windowToken != null) {
                            windowManager.removeView(alarmModeEnsuringView)
                        }
                    }
                }) {
                    Text("Cancel")
                }
            }
        }

        val lifecycleOwner = MyLifecycleOwner()
        lifecycleOwner.performRestore(null)
        lifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        ViewTreeLifecycleOwner.set(alarmModeEnsuringView, lifecycleOwner)
        alarmModeEnsuringView.setViewTreeSavedStateRegistryOwner(lifecycleOwner)
        val viewModelStore = ViewModelStore()
        ViewTreeViewModelStoreOwner.set(alarmModeEnsuringView) { viewModelStore }
        val coroutineContext = AndroidUiDispatcher.CurrentThread
        val runRecomposeScope = CoroutineScope(coroutineContext)
        val recomposer = Recomposer(coroutineContext)
        alarmModeEnsuringView.compositionContext = recomposer
        runRecomposeScope.launch {
            recomposer.runRecomposeAndApplyChanges()
        }

        val vibratorManager = getSystemService(VibratorManager::class.java) as VibratorManager
        vibratorManager.vibrate(CombinedVibration.createParallel(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE)))

        windowManager.addView(alarmModeEnsuringView, params)
    }
}