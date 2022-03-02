package com.goodylabs.android.interview.ui.characterlist

import androidx.annotation.StringRes
import com.goodylabs.android.interview.data.models.Character

data class CharacterListState(
    val isLoading: Boolean = false,
    val characters: List<Character> = emptyList(),
    @StringRes val errorMsgRes: Int? = null
)