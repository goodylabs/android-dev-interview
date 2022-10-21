package com.goodylabs.android.interview.data.repositories

import com.goodylabs.android.interview.data.models.Character
import com.goodylabs.android.interview.data.models.CharactersContainer
import retrofit2.Response

interface CharacterRepository {
    suspend fun getAllCharacters(page: Int): Response<CharactersContainer>
    suspend fun getCharacter(id: Int): Response<Character>
}