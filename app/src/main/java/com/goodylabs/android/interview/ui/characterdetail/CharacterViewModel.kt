package com.goodylabs.android.interview.ui.characterdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.goodylabs.android.interview.data.models.Character
import com.goodylabs.android.interview.data.repositories.CharacterRepositoryInterface
import com.goodylabs.android.interview.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(private val characterRepository: CharacterRepositoryInterface) :
    ViewModel() {

    fun idWasUpdated(id: Int): LiveData<Resource<Response<Character>>> {
        return characterRepository.getCharacterById(id)
    }
}