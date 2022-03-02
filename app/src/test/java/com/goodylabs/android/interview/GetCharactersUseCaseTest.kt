package com.goodylabs.android.interview

import com.goodylabs.android.interview.data.models.CharactersContainer
import com.goodylabs.android.interview.data.models.PageInfo
import com.goodylabs.android.interview.data.models.RequestState
import com.goodylabs.android.interview.data.repositories.CharacterRepository
import com.goodylabs.android.interview.data.usecase.GetCharactersUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import okhttp3.internal.EMPTY_RESPONSE
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class GetCharactersUseCaseTest {

    @MockK
    lateinit var repository: CharacterRepository

    lateinit var useCase: GetCharactersUseCase

    private val testSuccessResponse = CharactersContainer(PageInfo(123, 7, null, null), emptyList())

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = GetCharactersUseCase(repository)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun successPath() = runBlocking {
        coEvery { repository.getCharacterContainer(any()) } returns testSuccessResponse

        val resultStates = useCase(page = 1).toList()

        coVerify { repository.getCharacterContainer(any()) }
        assert(resultStates[0] is RequestState.Loading)
        val secondState = resultStates[1]
        assert(secondState is RequestState.Success && secondState.data == testSuccessResponse)
    }

    @Test
    fun networkErrorPath() = runBlocking {
        coEvery { repository.getCharacterContainer(any()) } throws IOException()

        val resultStates = useCase(page = 1).toList()

        coVerify { repository.getCharacterContainer(any()) }
        assert(resultStates[0] is RequestState.Loading)
        val secondState = resultStates[1]
        assert(secondState is RequestState.Error && secondState.errorMsgRes == R.string.network_error)
    }

    @Test
    fun httpErrorPath() = runBlocking {
        coEvery { repository.getCharacterContainer(any()) } throws HttpException(Response.error<CharactersContainer>(500, EMPTY_RESPONSE))

        val resultStates = useCase(page = 1).toList()

        coVerify { repository.getCharacterContainer(any()) }
        assert(resultStates[0] is RequestState.Loading)
        val secondState = resultStates[1]
        assert(secondState is RequestState.Error && secondState.errorMsgRes == R.string.server_error)
    }

}