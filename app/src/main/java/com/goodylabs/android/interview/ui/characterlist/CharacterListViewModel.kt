package com.goodylabs.android.interview.ui.characterlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goodylabs.android.interview.data.models.CharacterInfo
import com.goodylabs.android.interview.data.models.CharactersContainer
import com.goodylabs.android.interview.data.repositories.CharacterRepository
import com.goodylabs.android.interview.util.LiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(private val characterRepository: CharacterRepository) :
    ViewModel() {
    val liveEvent = LiveEvent<CharactersContainer>()

    fun getCharacters() {
        viewModelScope.launch {
            liveEvent.postValue(characterRepository.getCharacterContainer())
        }
    }
}