package com.freephoenix888.savemylife.data.datastore

import androidx.datastore.core.Serializer
import com.freephoenix888.savemylife.EmergencyMessagePreferences
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream
import kotlin.coroutines.cancellation.CancellationException

object EmergencyMessagePreferencesSerializer: Serializer<EmergencyMessagePreferences> {
    override val defaultValue: EmergencyMessagePreferences = EmergencyMessagePreferences.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): EmergencyMessagePreferences {
        try {
            return EmergencyMessagePreferences.parseFrom(input)
        } catch (e: InvalidProtocolBufferException) {
            throw CancellationException("Cannot reada proto.", e)
        }
    }

    override suspend fun writeTo(t: EmergencyMessagePreferences, output: OutputStream) = t.writeTo(output)
}