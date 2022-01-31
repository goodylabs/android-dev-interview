package com.goodylabs.android.interview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.goodylabs.android.interview.data.models.Character
import com.goodylabs.android.interview.data.models.CharactersContainer
import com.goodylabs.android.interview.data.repositories.CharacterRepositoryInterface
import com.goodylabs.android.interview.util.Resource
import com.goodylabs.android.interview.util.Status
import retrofit2.Response

class TestCharacterRepository: CharacterRepositoryInterface {


    private val observableCharacters = MutableLiveData<Resource<Response<Character>>>()


    override fun getCharacterById(id: Int): LiveData<Resource<Response<Character>>> {
        observableCharacters.postValue(Resource(Status.SUCCESS, null, "message"))

        return observableCharacters
    }

    override fun getCharacterList(): LiveData<Resource<Response<CharactersContainer>>> {
        TODO("Not yet implemented")
    }

}