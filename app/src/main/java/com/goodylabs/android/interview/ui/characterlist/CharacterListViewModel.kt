package com.goodylabs.android.interview.ui.characterlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goodylabs.android.interview.data.models.Character
import com.goodylabs.android.interview.data.models.CharactersContainer
import com.goodylabs.android.interview.data.models.RequestState
import com.goodylabs.android.interview.data.usecase.GetCharactersUseCase
import com.goodylabs.android.interview.util.LiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {

    private val _state = MutableLiveData(CharacterListState(isLoading = true))
    val state: LiveData<CharacterListState> = _state

    val goToDetailsEvent = LiveEvent<Int>()

    private var lastPage: Int = Int.MAX_VALUE
    private var page: Int = 1

    init {
        loadNextPage()
    }

    fun tryToLoadMore() {
        if (_state.value?.isLoading == true ) return // Already loading
        if (page > lastPage) return // Last page is reached
        loadNextPage()
    }

    fun selectCharacter(character: Character) {
        goToDetailsEvent.postValue(character.id)
    }

    private fun loadNextPage() {
        viewModelScope.launch {
            getCharactersUseCase(page).collect { requestState ->
                handleRequestState(requestState)
            }
        }
    }

    private fun handleRequestState(requestState: RequestState<CharactersContainer>) {
        when (requestState) {
            is RequestState.Error -> _state.value =
                _state.value?.copy(errorMsgRes = requestState.errorMsgRes, isLoading = false)
            is RequestState.Loading -> _state.value =
                _state.value?.copy(errorMsgRes = null, isLoading = true)
            is RequestState.Success -> {
                page++
                lastPage = requestState.data.info.pages ?: Int.MAX_VALUE
                _state.value = _state.value?.let {
                    it.copy(
                        errorMsgRes = null,
                        isLoading = false,
                        characters = it.characters + requestState.data.results
                    )
                }
            }
        }
    }

}