package com.goodylabs.android.interview.ui.characterlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.goodylabs.android.interview.data.models.CharactersContainer
import com.goodylabs.android.interview.data.repositories.CharacterRepository
import com.goodylabs.android.interview.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(private val characterRepository: CharacterRepository) :
    ViewModel() {

    private val _characterLiveData = characterRepository.getCharacterList()
    val charactersLiveData: LiveData<Resource<Response<CharactersContainer>>>
        get() = _characterLiveData

}