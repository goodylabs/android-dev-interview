package com.goodylabs.android.interview.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Character(
    val id: Int,
    val name: String,
    val status: CharacterStatus,
    val image: String?
)