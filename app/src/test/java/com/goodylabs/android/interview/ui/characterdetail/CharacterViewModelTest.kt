package com.goodylabs.android.interview.ui.characterdetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.goodylabs.android.interview.TestCharacterRepository
import com.goodylabs.android.interview.getOrAwaitValueTest
import com.goodylabs.android.interview.util.Status
import io.mockk.MockKAnnotations
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CharacterViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private var repo = TestCharacterRepository()
    private lateinit var viewModel: CharacterViewModel

    @Before
    fun setup() {

        MockKAnnotations.init(this, relaxed = true)
        viewModel = CharacterViewModel(repo)
    }

    @Test
    fun `when idUpdated is called, returns success`() {

        val value = viewModel.idWasUpdated(1).getOrAwaitValueTest()
        assertEquals(value.status, Status.SUCCESS)
    }

    @Test
    fun `when idUpdated is called, returns massage`() {

        val value = viewModel.idWasUpdated(1).getOrAwaitValueTest()
        assertNotNull(value.message)
    }

}