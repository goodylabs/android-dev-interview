package com.goodylabs.android.interview.ui.characterdetails

import androidx.lifecycle.SavedStateHandle
import com.goodylabs.android.interview.MainDispatcherRule
import com.goodylabs.android.interview.data.repositories.CharacterRepositoryMock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterDetailsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: CharacterDetailsViewModel
    private lateinit var repositoryMock: CharacterRepositoryMock

    @Before
    fun setup() {
        repositoryMock = CharacterRepositoryMock()
    }

    @Test
    fun fetchCharacterDetails_successful() = runTest {
        viewModel = CharacterDetailsViewModel(
            repositoryMock,
            SavedStateHandle(mapOf("selectedCharacterId" to 0))
        )

        val expectedCharacter = CharacterRepositoryMock.character
        assertEquals(expectedCharacter, viewModel.character.value)
        assertFalse(viewModel.isLoading.value)
        assertFalse(viewModel.hasError.value)
    }

    @Test
    fun fetchCharacterDetails_failure() = runTest {
        repositoryMock.setDetailsError()

        viewModel = CharacterDetailsViewModel(
            repositoryMock,
            SavedStateHandle(mapOf("selectedCharacterId" to 0))
        )

        assertTrue(viewModel.hasError.value)
        assertNull(viewModel.character.value)
        assertFalse(viewModel.isLoading.value)
    }
}
