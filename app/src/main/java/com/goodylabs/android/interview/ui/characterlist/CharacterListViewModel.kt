package com.goodylabs.android.interview.ui.characterlist

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goodylabs.android.interview.data.models.Character
import com.goodylabs.android.interview.data.repositories.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

const val TAG = "CharacterListViewModel"

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel() {

    private var _error = MutableSharedFlow<String>()
    val error: SharedFlow<String> get() = _error.asSharedFlow()

    private var _characterList = MutableLiveData<MutableList<Character>>(mutableListOf())
    val characterList: LiveData<MutableList<Character>> get() = _characterList



    init {
        setupUi()
    }

    private fun setupUi() = viewModelScope.launch {

        var page = 1
        while (true) {
            val response = characterRepository.getAllCharacters(page)
            if (response.isSuccessful) {

                response.body()!!.results!!.map {
                    _characterList.value?.add(it)
                }

                _characterList.value = _characterList.value
                page++
                Log.d(TAG, "setupUi: ${response.body()!!.results?.get(0)?.name}")
                if (response.body()!!.info?.next.isNullOrEmpty()) return@launch
            } else {
                Log.e(TAG, "setupUi: ${response.errorBody()!!.charStream().readText()}")
                _error.emit("Failed retrieving character list.")
            }
        }
    }
}