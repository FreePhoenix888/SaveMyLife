package com.freephoenix888.savemylife.services

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.graphics.PixelFormat
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.provider.Telephony
import android.view.WindowManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.*
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import com.freephoenix888.savemylife.MyLifecycleOwner
import com.freephoenix888.savemylife.R
import com.freephoenix888.savemylife.broadcastReceivers.AlarmBroadcastReceiver
import com.freephoenix888.savemylife.broadcastReceivers.PowerButtonBroadcastReceiver
import com.freephoenix888.savemylife.broadcastReceivers.RestartBroadcastReceiver
import com.freephoenix888.savemylife.broadcastReceivers.SmsBroadcastReceiver
import com.freephoenix888.savemylife.constants.ActionConstants
import com.freephoenix888.savemylife.constants.MessageConstants
import com.freephoenix888.savemylife.constants.NotificationConstants
import com.freephoenix888.savemylife.constants.NotificationConstants.CHANNEL_ID
import com.freephoenix888.savemylife.domain.models.PhoneNumber
import com.freephoenix888.savemylife.domain.useCases.*
import com.freephoenix888.savemylife.ui.SaveMyLifeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
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

    private val windowManager by lazy {
        applicationContext.getSystemService(Context.WINDOW_SERVICE)
                as WindowManager
    }


    override fun onCreate() {
        super.onCreate()
        showOverlay()
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
        lifecycleScope.launch(Dispatchers.IO) {
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
        lifecycleScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.IO) {
                getPhoneNumberListFlowUseCase().collect {
                    phoneNumberList.value = it
                }
            }
        }
        lifecycleScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.IO) {
                getIsAlarmModeEnabledFlowUseCase().collect {
                    isAlarmModeEnabled.value = it
                    alarmManager =
                        applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
                    if (alarmIntent != null) {
                        alarmManager?.cancel(alarmIntent)
                    }
                    if (it) {
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
        }
        lifecycleScope.launch(Dispatchers.IO) {
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
        val notifyIntent = Intent(this, SaveMyLifeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val notifyPendingIntent = PendingIntent.getActivity(
            this, 0, notifyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
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
            .setContentIntent(notifyPendingIntent)
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
                val importance = NotificationManager.IMPORTANCE_DEFAULT
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
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            layoutFlag,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )

        val composeView = ComposeView(this)
        composeView.setContent {
            Text(
                text = "Hello",
                color = Color.Black,
                fontSize = 50.sp,
                modifier = Modifier
                    .wrapContentSize()
                    .background(Color.Green)
            )
        }

        val viewModelStore = ViewModelStore()
        val lifecycleOwner = MyLifecycleOwner()
        lifecycleOwner.performRestore(null)
        lifecycleOwner.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        ViewTreeLifecycleOwner.set(composeView, lifecycleOwner)
        ViewTreeViewModelStoreOwner.set(composeView) { viewModelStore }
        composeView.setViewTreeSavedStateRegistryOwner(lifecycleOwner)

        windowManager.addView(composeView, params)
    }
}