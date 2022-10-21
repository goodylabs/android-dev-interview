package com.goodylabs.android.interview.ui.characterinfo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goodylabs.android.interview.data.repositories.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

const val TAG = "CharacterInfoViewModel"

@HiltViewModel
class CharacterInfoViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {

    data class UiState(
        var avatar: String? = null,
        var name: String? = null,
        var status: String? = null,
        var gender: String? = null,
        var species: String? = null,
        var created: String? = null
    )

    private var _uiState = MutableLiveData(UiState())
    val uiState: LiveData<UiState> get() = _uiState

    fun getCharacterInfo(id: Int?) = viewModelScope.launch {
        val response = repository.getCharacter(id!!)

        if (response.isSuccessful) {
            response.body()!!.let { character ->
                _uiState.postValue(
                    UiState(
                        avatar = character.image,
                        name = character.name,
                        status = character.status,
                        gender = character.gender,
                        species = character.species,
                        created = character.created
                    )
                )
                Log.d(TAG, "getCharacterInfo: ${response.body()!!}")
            }
        } else {
            Log.e(TAG, "getCharacterInfo: ${response.errorBody()!!.charStream().readText()}")
        }
    }
}