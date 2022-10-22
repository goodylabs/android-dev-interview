package com.goodylabs.android.interview.util

import java.text.SimpleDateFormat
import java.util.*

object DateFormatter {

    fun format(date: String?): String? {
        if (date.isNullOrEmpty()) return null
        val oldFormat = SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US
        )
        val oldDate = oldFormat.parse(date)

        val newFormat = SimpleDateFormat("dd-MM-yyy")

        return oldDate?.let { newFormat.format(it) }
    }
}