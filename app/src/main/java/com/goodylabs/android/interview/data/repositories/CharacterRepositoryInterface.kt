package com.goodylabs.android.interview.data.repositories

import androidx.lifecycle.LiveData
import com.goodylabs.android.interview.data.models.Character
import com.goodylabs.android.interview.data.models.CharactersContainer
import com.goodylabs.android.interview.util.Resource
import retrofit2.Response

interface CharacterRepositoryInterface {
    fun getCharacterList(): LiveData<Resource<Response<CharactersContainer>>>
    fun getCharacterById(id: Int): LiveData<Resource<Response<Character>>>
}