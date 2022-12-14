package com.freephoenix888.savemylife.broadcastReceivers

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.freephoenix888.savemylife.domain.useCases.DoOnDangerUseCase
import com.freephoenix888.savemylife.domain.useCases.GetMessageSendingIntervalFlowUseCase
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
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

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val alarmManager: AlarmManager by lazy { applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager }

    override fun onReceive(context: Context?, intent: Intent?) {
        scope.launch {
            Log.d(null, "DangerBroadcastReceiver onReceive: \n${System.currentTimeMillis()} \n${getMessageSendingIntervalFlowUseCase().first().inWholeMilliseconds} \n ${System.currentTimeMillis() + getMessageSendingIntervalFlowUseCase().first().inWholeMilliseconds}")
            doOnDangerUseCase()
 
            val intent = Intent(applicationContext, DangerBroadcastReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                applicationContext,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC,
                System.currentTimeMillis() + getMessageSendingIntervalFlowUseCase().first().inWholeMilliseconds,
                pendingIntent
            )
        }
    }

}