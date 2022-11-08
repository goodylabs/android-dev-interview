package com.goodylabs.android.interview.data.api

import com.goodylabs.android.interview.data.models.CharactersContainer
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface CharacterService {
    @GET("character")
    suspend fun getCharacterContainer(): CharactersContainer
}