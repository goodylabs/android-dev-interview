package com.goodylabs.android.interview.data.repositories

import com.goodylabs.android.interview.data.Result
import com.goodylabs.android.interview.data.models.Character
import com.goodylabs.android.interview.data.models.CharactersContainer
import com.goodylabs.android.interview.data.models.Location
import com.goodylabs.android.interview.data.models.Origin
import com.goodylabs.android.interview.data.models.Status
import java.time.LocalDateTime
import javax.inject.Singleton

@Singleton
class CharacterRepositoryMock : CharacterRepository {

    private var result: Result<CharactersContainer> = Result.Success(
        CharactersContainer(
            info = null,
            results = listOf(character)
        )
    )

    private var detailsResult: Result<Character> = Result.Success(character)

    override suspend fun getCharacterContainer(page: Int): Result<CharactersContainer> {
        return result
    }

    override suspend fun getCharacterDetails(id: Int): Result<Character> {
        return detailsResult
    }

    fun setError() {
        result = Result.Error(Exception())
    }

    fun setDetailsError() {
        detailsResult = Result.Error(Exception())
    }

    companion object {
        val character = Character(
            id = 1,
            name = "Rick Sanchez",
            status = Status.ALIVE,
            species = "Human",
            type = "",
            gender = "Male",
            origin = Origin(name = "Earth"),
            location = Location(name = "Earth"),
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            url = "https://rickandmortyapi.com/api/character/1",
            created = LocalDateTime.now()
        )
    }
}
