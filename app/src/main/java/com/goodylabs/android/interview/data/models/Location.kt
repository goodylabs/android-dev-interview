package com.goodylabs.android.interview.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Location(

    @field: Json(name = "name")
    val name: String,

    @field: Json(name = "url")
    val url: String
)