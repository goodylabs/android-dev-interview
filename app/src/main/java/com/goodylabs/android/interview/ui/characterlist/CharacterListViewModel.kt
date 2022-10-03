package com.goodylabs.android.interview.ui.characterlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.goodylabs.android.interview.data.repositories.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharacterListViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel() {

    val characters = Pager(PagingConfig(pageSize = 20)) {
        CharacterPagingSource(characterRepository)
    }.flow.cachedIn(viewModelScope)
}
