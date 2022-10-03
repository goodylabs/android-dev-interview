package com.goodylabs.android.interview.data.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.LocalDateTime

@JsonClass(generateAdapter = true)
data class Character(
    val id: Int,
    val name: String,
    val status: Status,
    val species: String,
    val type: String,
    val gender: String,
    val origin: Origin,
    val location: Location,
    val image: String,
    val url: String,
    val created: LocalDateTime
)

@JsonClass(generateAdapter = true)
data class Origin(
    val name: String
)

@JsonClass(generateAdapter = true)
data class Location(
    val name: String
)

@JsonClass(generateAdapter = false)
enum class Status {
    @Json(name = "Alive")
    ALIVE,

    @Json(name = "Dead")
    DEAD,

    @Json(name = "unknown")
    UNKNOWN
}
