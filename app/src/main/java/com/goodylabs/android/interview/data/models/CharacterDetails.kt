package com.goodylabs.android.interview.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CharacterDetails(
    val id: Int,
    val name: String,
    val status: CharacterStatus,
    val image: String?,
    val gender: Gender?,
    val type: String,
    val species: String,
    @Json(name = "location")
    val lastKnownLocation: Location,
    @Json(name = "origin")
    val originLocation: Location
)