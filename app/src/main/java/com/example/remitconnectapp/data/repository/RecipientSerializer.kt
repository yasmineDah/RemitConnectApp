package com.example.remitconnectapp.data.repository

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.example.remitconnectapp.domain.model.Recipient
import kotlinx.serialization.SerializationException
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream


object RecipientListSerializer : Serializer<List<Recipient>> {
    // default value of the settings for initialization
    override val defaultValue: List<Recipient> = emptyList()

    // reading the inputstream of the stored file
    override suspend fun readFrom(input: InputStream): List<Recipient> {
        try {
            return Json.decodeFromString(
                deserializer = ListSerializer(Recipient.serializer()),
                string = input.readBytes().decodeToString()
            )
        } catch (exception: SerializationException) {
            throw CorruptionException("Error occurred during decoding the storage", exception)
        }
    }

    // writing to the output stream with actual datatype
    override suspend fun writeTo(
        t: List<Recipient>,
        output: OutputStream
    ) = output.write(
        Json.encodeToString(serializer = ListSerializer(Recipient.serializer()), value = t).toByteArray()
    )
}


val Context.recipientsDataStore: DataStore<List<Recipient>> by dataStore(
    fileName = "recipients.json",
    serializer = RecipientListSerializer
)

