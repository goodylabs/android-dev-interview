package com.goodylabs.android.interview.ui.characterdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goodylabs.android.interview.data.Result
import com.goodylabs.android.interview.data.models.Character
import com.goodylabs.android.interview.data.repositories.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val characterId = savedStateHandle.getStateFlow("selectedCharacterId", 0)

    private val _character = MutableStateFlow<Character?>(null)
    val character: StateFlow<Character?> = _character.asStateFlow()

    private val _hasError = MutableStateFlow<Boolean>(false)
    val hasError: StateFlow<Boolean> = _hasError.asStateFlow()

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        fetchCharacterDetails(characterId.value)
    }

    private fun fetchCharacterDetails(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _hasError.value = false

            val result = characterRepository.getCharacterDetails(id)

            when (result) {
                is Result.Success -> {
                    _character.value = result.data
                    _isLoading.value = false
                }
                is Result.Error -> {
                    _hasError.value = true
                    _isLoading.value = false
                }
            }
        }
    }
}
