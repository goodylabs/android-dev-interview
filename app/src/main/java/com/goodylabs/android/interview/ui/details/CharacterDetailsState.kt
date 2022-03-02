package com.goodylabs.android.interview.ui.details

import androidx.annotation.StringRes
import com.goodylabs.android.interview.data.models.CharacterDetails

data class CharacterDetailsState(
    val character: CharacterDetails? = null,
    val isLoading: Boolean = false,
    @StringRes val errorMsg: Int? = null
)