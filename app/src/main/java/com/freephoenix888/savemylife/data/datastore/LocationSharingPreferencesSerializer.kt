package com.freephoenix888.savemylife.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.freephoenix888.savemylife.LocationSharingPreferences
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream
import kotlin.coroutines.cancellation.CancellationException

object LocationSharingPreferencesSerializer: Serializer<LocationSharingPreferences> {
    override val defaultValue: LocationSharingPreferences = LocationSharingPreferences.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): LocationSharingPreferences {
        try {
            return LocationSharingPreferences.parseFrom(input)
        } catch (e: InvalidProtocolBufferException) {
            throw CancellationException("Cannot reada proto.", e)
        }
    }

    override suspend fun writeTo(t: LocationSharingPreferences, output: OutputStream) = t.writeTo(output)

    val Context.locationSharingPreferencesDataStore: DataStore<LocationSharingPreferences> by dataStore(
        fileName = "location_sharing_preferences.pb",
        serializer = LocationSharingPreferencesSerializer
    )
}