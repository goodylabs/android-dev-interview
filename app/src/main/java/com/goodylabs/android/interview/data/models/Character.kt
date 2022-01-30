package com.goodylabs.android.interview.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Character(

    @field: Json(name = "id")
    val id: Int,

    @field: Json(name = "image")
    val image: String,

    @field: Json(name = "name")
    val name: String,

    @field: Json(name = "status")
    val status: String,

    @field: Json(name = "species")
    val species: String,

    @field: Json(name = "gender")
    val gender: String,

    @field: Json(name = "location")
    val location: Location,

    @field: Json(name = "created")
    val created: String,

    @field: Json(name = "url")
    val url: String
)
