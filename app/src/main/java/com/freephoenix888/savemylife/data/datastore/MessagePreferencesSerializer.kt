package com.freephoenix888.savemylife.data.datastore

import androidx.datastore.core.Serializer
import com.freephoenix888.savemylife.MessagePreferences
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream
import kotlin.coroutines.cancellation.CancellationException

object MessagePreferencesSerializer: Serializer<MessagePreferences> {
    override val defaultValue: MessagePreferences = MessagePreferences.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): MessagePreferences {
        try {
            return MessagePreferences.parseFrom(input)
        } catch (e: InvalidProtocolBufferException) {
            throw CancellationException("Cannot reada proto.", e)
        }
    }

    override suspend fun writeTo(t: MessagePreferences, output: OutputStream) = t.writeTo(output)
}