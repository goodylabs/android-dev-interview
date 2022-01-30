package com.goodylabs.android.interview.data.api

import com.goodylabs.android.interview.data.models.Character
import com.goodylabs.android.interview.data.models.CharactersContainer
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Singleton

@Singleton
interface CharacterService {

    @GET("character")
    suspend fun getCharacterContainer(): Response<CharactersContainer>

    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: Int): Response<Character>

}