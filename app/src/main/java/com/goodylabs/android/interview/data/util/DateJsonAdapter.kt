package com.goodylabs.android.interview.data.util

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DateJsonAdapter : JsonAdapter<LocalDateTime>() {

    @FromJson
    @Synchronized
    override fun fromJson(reader: JsonReader): LocalDateTime? {
        return LocalDateTime.parse(reader.nextString(), DateTimeFormatter.ISO_DATE_TIME)
    }

    @ToJson
    @Synchronized
    override fun toJson(writer: JsonWriter, value: LocalDateTime?) {
        writer.value(value?.format(DateTimeFormatter.ISO_DATE_TIME))
    }
}
