package com.goodylabs.android.interview.ui.characterlist

import android.os.Build
import com.goodylabs.android.interview.MainDispatcherRule
import com.goodylabs.android.interview.data.repositories.CharacterRepositoryMock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.R])
@OptIn(ExperimentalCoroutinesApi::class)
class CharacterListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: CharacterListViewModel
    private lateinit var repositoryMock: CharacterRepositoryMock

    @Before
    fun setup() {
        repositoryMock = CharacterRepositoryMock()
    }

    @Test
    fun fetchCharacters_successful() = runTest {
        viewModel = CharacterListViewModel(repositoryMock)
        val adapter = CharacterListAdapter(onClick = {})
        val expectedCharacter = CharacterRepositoryMock.character
        val job = launch {
            viewModel.characters.collectLatest {
                adapter.submitData(it)
            }
        }
        advanceUntilIdle()

        assertEquals(listOf(expectedCharacter), adapter.snapshot())

        job.cancel()
    }

    @Test
    fun fetchCharacters_verifyThatErrorOccurred_listIsEmpty() = runTest {
        repositoryMock.setError()

        viewModel = CharacterListViewModel(repositoryMock)
        val adapter = CharacterListAdapter(onClick = {})
        val job = launch {
            viewModel.characters.collectLatest {
                adapter.submitData(it)
            }
        }
        advanceUntilIdle()

        assertTrue(adapter.snapshot().isEmpty())

        job.cancel()
    }
}
