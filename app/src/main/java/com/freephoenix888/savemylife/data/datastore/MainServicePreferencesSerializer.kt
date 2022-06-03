package com.freephoenix888.savemylife.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.freephoenix888.savemylife.MainServicePreferences
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream
import kotlin.coroutines.cancellation.CancellationException

object MainServicePreferencesSerializer: Serializer<MainServicePreferences> {
    override val defaultValue: MainServicePreferences = MainServicePreferences.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): MainServicePreferences {
        try {
            return MainServicePreferences.parseFrom(input)
        } catch (e: InvalidProtocolBufferException) {
            throw CancellationException("Cannot reada proto.", e)
        }
    }

    override suspend fun writeTo(t: MainServicePreferences, output: OutputStream) = t.writeTo(output)

    val Context.mainServicePreferencesDataStore: DataStore<MainServicePreferences> by dataStore(
        fileName = "location_sharing_preferences.pb",
        serializer = MainServicePreferencesSerializer
    )
}