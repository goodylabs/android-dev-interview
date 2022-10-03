package com.goodylabs.android.interview.data.repositories

import com.goodylabs.android.interview.data.Result
import com.goodylabs.android.interview.data.api.CharacterService
import com.goodylabs.android.interview.data.models.Character
import com.goodylabs.android.interview.data.models.CharactersContainer
import com.goodylabs.android.interview.data.performRequest
import javax.inject.Inject
import javax.inject.Singleton

interface CharacterRepository {

    suspend fun getCharacterContainer(page: Int): Result<CharactersContainer>

    suspend fun getCharacterDetails(id: Int): Result<Character>
}

@Singleton
class CharacterRepositoryImpl @Inject constructor(
    private val characterService: CharacterService
) : CharacterRepository {

    override suspend fun getCharacterContainer(page: Int): Result<CharactersContainer> {
        return performRequest { characterService.getCharacterContainer(page) }
    }

    override suspend fun getCharacterDetails(id: Int): Result<Character> {
        return performRequest { characterService.getCharacterDetails(id) }
    }
}
