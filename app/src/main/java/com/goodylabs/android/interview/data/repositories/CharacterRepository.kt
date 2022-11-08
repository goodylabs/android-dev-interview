package com.goodylabs.android.interview.data.repositories

import com.goodylabs.android.interview.data.api.CharacterService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterRepository @Inject constructor(private val characterService: CharacterService) {
    // I am not sure whether I am allowed to add an interface for this repo
    // (would help a lot with testing)
    // so I guess I will just leave it as it is
    suspend fun getCharacterContainer() = characterService.getCharacterContainer()
}