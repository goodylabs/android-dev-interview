package com.goodylabs.android.interview.data.repositories

import com.goodylabs.android.interview.data.api.CharacterService
import com.goodylabs.android.interview.data.models.Character
import com.goodylabs.android.interview.data.models.CharactersContainer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterRepositoryImpl @Inject constructor(
    private val characterService: CharacterService
): CharacterRepository {

    override suspend fun getAllCharacters(page: Int): Response<CharactersContainer> {
        return withContext(Dispatchers.IO) {
            characterService.getCharacterContainer(page)
        }
    }

    override suspend fun getCharacter(id: Int): Response<Character> {
        return withContext(Dispatchers.IO) {
            characterService.getCharacter(id)
        }
    }
}
