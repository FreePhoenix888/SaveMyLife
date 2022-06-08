package com.freephoenix888.savemylife.data.datastore

import androidx.datastore.core.Serializer
import com.freephoenix888.savemylife.SaveMyLifePreferences
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream
import kotlin.coroutines.cancellation.CancellationException

object SaveMyLifePreferencesSerializer: Serializer<SaveMyLifePreferences> {
    override val defaultValue: SaveMyLifePreferences = SaveMyLifePreferences.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): SaveMyLifePreferences {
        try {
            return SaveMyLifePreferences.parseFrom(input)
        } catch (e: InvalidProtocolBufferException) {
            throw CancellationException("Cannot reada proto.", e)
        }
    }

    override suspend fun writeTo(t: SaveMyLifePreferences, output: OutputStream) = t.writeTo(output)
}