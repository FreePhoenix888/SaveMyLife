package com.freephoenix888.savemylife.broadcastReceivers

import android.app.AlarmManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.widget.Toast
import com.freephoenix888.savemylife.domain.useCases.DoOnDangerUseCase
import com.freephoenix888.savemylife.domain.useCases.GetDangerBroadcastReceiverPendingIntent
import com.freephoenix888.savemylife.domain.useCases.GetMessageSendingIntervalFlowUseCase
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@AndroidEntryPoint
class DangerBroadcastReceiver:
    BroadcastReceiver() {

    @Inject
    @ApplicationContext
    lateinit var applicationContext: Context

    @Inject
    lateinit var doOnDangerUseCase: DoOnDangerUseCase

    @Inject
    lateinit var getMessageSendingIntervalFlowUseCase: GetMessageSendingIntervalFlowUseCase

    @Inject
    lateinit var getDangerBroadcastReceiverPendingIntent: GetDangerBroadcastReceiverPendingIntent

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val alarmManager: AlarmManager by lazy { applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager }

    override fun onReceive(context: Context?, intent: Intent?) {
        scope.launch {
            try {
                Timber.d("DangerBroadcastReceiver onReceive: \n${System.currentTimeMillis()} \n${getMessageSendingIntervalFlowUseCase().first().inWholeMilliseconds} \n ${System.currentTimeMillis() + getMessageSendingIntervalFlowUseCase().first().inWholeMilliseconds}")
                doOnDangerUseCase()
            } catch (e: Exception) {
                // Handle exceptions from doOnDangerUseCase
                Timber.e("Error in doOnDangerUseCase: $e")
            }

            // Check SCHEDULE_EXACT_ALARM
            if (Build.VERSION.SDK_INT >= 31) {
                if (!hasScheduleExactAlarmPermission()){
                    showPermissionError()
                }
            }

            val pendingIntent = getDangerBroadcastReceiverPendingIntent()
            @Suppress("ScheduleExactAlarm")
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC,
                System.currentTimeMillis() + getMessageSendingIntervalFlowUseCase().first().inWholeMilliseconds,
                pendingIntent
            )
        }
    }

    private fun hasScheduleExactAlarmPermission(): Boolean {
        return PackageManager.PERMISSION_GRANTED == applicationContext.checkCallingPermission("android.permission.SCHEDULE_EXACT_ALARM")
    }

    private fun showPermissionError() {
        // Display a toast message indicating the permission error
        Toast.makeText(applicationContext, "SCHEDULE_EXACT_ALARM permission not granted", Toast.LENGTH_SHORT)
            .show()
    }

}