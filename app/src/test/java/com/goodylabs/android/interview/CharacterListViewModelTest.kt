package com.goodylabs.android.interview

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.goodylabs.android.interview.data.models.*
import com.goodylabs.android.interview.data.usecase.GetCharactersUseCase
import com.goodylabs.android.interview.ui.characterlist.CharacterListViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.unmockkAll
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
class CharacterListViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    @get:Rule var rule: TestRule = InstantTaskExecutorRule()

    @MockK
    lateinit var getCharactersUseCase: GetCharactersUseCase

    private lateinit var viewModel: CharacterListViewModel

    private val pageOneSuccessResponse = CharactersContainer(
        PageInfo(4, 2), listOf(
            Character(1, "Char1", CharacterStatus.Alive, "img1"),
            Character(2, "Char2", CharacterStatus.Alive, "img2")
        )
    )
    private val pageTwoSuccessResponse = CharactersContainer(
        PageInfo(4, 2), listOf(
            Character(3, "Char3", CharacterStatus.Alive, "img3"),
            Character(4, "Char4", CharacterStatus.Alive, "img4")
        )
    )

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
        Dispatchers.resetMain()
    }

    @Test
    fun initShouldLoadData() = testDispatcher.run {
        setupSuccessScenario()

        viewModel = CharacterListViewModel(getCharactersUseCase)

        coVerify { getCharactersUseCase.invoke(1) }
    }

    @Test
    fun tryToLoadMore() = testDispatcher.run {
        setupSuccessScenario()

        viewModel = CharacterListViewModel(getCharactersUseCase)
        viewModel.tryToLoadMore()

        coVerify { getCharactersUseCase.invoke(2) }
    }

    @Test
    fun tryToLoadMoreSecondTimeShouldNotWork() = testDispatcher.run {
        setupSuccessScenario()

        viewModel = CharacterListViewModel(getCharactersUseCase)
        viewModel.tryToLoadMore()
        viewModel.tryToLoadMore()

        coVerify(atMost = 2) { getCharactersUseCase.invoke(any()) }
    }

    @Test
    fun checkStateAfterLoad() = testDispatcher.run {
        setupSuccessScenario()

        viewModel = CharacterListViewModel(getCharactersUseCase)
        viewModel.tryToLoadMore()

        val state = viewModel.state.value
        assert(state != null && state.characters == pageOneSuccessResponse.results.plus(pageTwoSuccessResponse.results))
    }

    private fun setupSuccessScenario() {
        coEvery { getCharactersUseCase(1) } returns flow {
            emit(RequestState.Loading())
            emit(RequestState.Success(pageOneSuccessResponse))
        }
        coEvery { getCharactersUseCase(2) } returns flow {
            emit(RequestState.Loading())
            emit(RequestState.Success(pageTwoSuccessResponse))
        }
        coEvery { getCharactersUseCase(3) } returns flow {
            emit(RequestState.Loading())
            emit(RequestState.Error(R.string.server_error))
        }
    }

}