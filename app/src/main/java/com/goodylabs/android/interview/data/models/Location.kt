package com.goodylabs.android.interview.data.models

import com.squareup.moshi.JsonClass
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class Location(
    val name: String,
    val url: String
): Serializable