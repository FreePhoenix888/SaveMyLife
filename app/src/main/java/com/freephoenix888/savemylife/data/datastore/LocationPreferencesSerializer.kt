package com.freephoenix888.savemylife.data.datastore

import androidx.datastore.core.Serializer
import com.freephoenix888.savemylife.LocationPreferences
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream
import kotlin.coroutines.cancellation.CancellationException

object LocationPreferencesSerializer: Serializer<LocationPreferences> {
    override val defaultValue: LocationPreferences = LocationPreferences.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): LocationPreferences {
        try {
            return LocationPreferences.parseFrom(input)
        } catch (e: InvalidProtocolBufferException) {
            throw CancellationException("Cannot reada proto.", e)
        }
    }

    override suspend fun writeTo(t: LocationPreferences, output: OutputStream) = t.writeTo(output)
}