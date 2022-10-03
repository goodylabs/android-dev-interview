package com.goodylabs.android.interview.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

private val dateFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)

val LocalDateTime.formattedDate: String
    get() = format(dateFormatter)
