package com.freephoenix888.savemylife.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.freephoenix888.savemylife.Utils
import com.freephoenix888.savemylife.domain.useCases.GetMessageUseCase
import com.freephoenix888.savemylife.domain.useCases.GetPhoneNumberListFlowUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

@HiltWorker
class DoInDangerWorker @AssistedInject constructor(
    @Assisted applicationContext: Context,
    @Assisted workerParams: WorkerParameters,
    val getPhoneNumberListFlowUseCase: GetPhoneNumberListFlowUseCase,
    val getMessageUseCase: GetMessageUseCase,
) : CoroutineWorker(applicationContext, workerParams) {
    override suspend fun doWork(): Result {
        withContext(Dispatchers.IO) {
            Log.d(null,"DoInDangerWorker doWork: ")
            val phoneNumberList = getPhoneNumberListFlowUseCase().first()
            for (phoneNumber in phoneNumberList) {
                Log.d(null, "DoInDangerWorker doWork: phoneNumber: $phoneNumber")
                Utils.sendSms(
                    context = applicationContext,
                    phoneNumber = phoneNumber.phoneNumber,
                    message = getMessageUseCase(phoneNumber)
                )
            }
        }
        return Result.success()
    }
}