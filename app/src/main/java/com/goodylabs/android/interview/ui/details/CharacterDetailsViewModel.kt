package com.goodylabs.android.interview.ui.details

import androidx.lifecycle.*
import com.goodylabs.android.interview.data.models.RequestState
import com.goodylabs.android.interview.data.usecase.GetCharacterDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterDetailsViewModel @Inject constructor(
    private val getCharacterDetailsUseCase: GetCharacterDetailsUseCase,
    stateHandle: SavedStateHandle
): ViewModel() {

    private val _state = MutableLiveData(CharacterDetailsState())
    val state: LiveData<CharacterDetailsState> = _state

    private val id by lazy { CharacterDetailsFragmentArgs.fromSavedStateHandle(stateHandle).id }

    init {
        loadDetails()
    }

    fun tryAgain() {
        loadDetails()
    }

    private fun loadDetails() {
        viewModelScope.launch {
            getCharacterDetailsUseCase(id).collect { requestState ->
                when (requestState) {
                    is RequestState.Success -> _state.value = CharacterDetailsState(requestState.data)
                    is RequestState.Loading -> _state.value = CharacterDetailsState(isLoading = true)
                    is RequestState.Error -> _state.value = CharacterDetailsState(errorMsg = requestState.errorMsgRes)
                }
            }
        }

    }

}