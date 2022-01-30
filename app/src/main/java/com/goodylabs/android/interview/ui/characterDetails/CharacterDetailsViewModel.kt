package com.goodylabs.android.interview.ui.characterDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goodylabs.android.interview.data.models.Character
import com.goodylabs.android.interview.data.repositories.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(private val characterRepository: CharacterRepository) :
    ViewModel() {

    private val _character by lazy {
        MutableLiveData<Character>()
    }

    val character: LiveData<Character> by lazy {
        _character
    }

    fun getCharacter(characterId: Int) {
        val job: Job = viewModelScope.launch {
            _character.value = characterRepository.getSingleCharacter(characterId)
        }
    }
}