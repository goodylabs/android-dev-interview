package com.goodylabs.android.interview.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.goodylabs.android.interview.data.api.CharacterService
import com.goodylabs.android.interview.data.models.Character
import com.goodylabs.android.interview.data.models.CharactersContainer
import com.goodylabs.android.interview.util.Resource
import kotlinx.coroutines.Dispatchers
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterRepository @Inject constructor(private val characterService: CharacterService) :
    CharacterRepositoryInterface {

    suspend fun getCharacterContainer() = characterService.getCharacterContainer()
    suspend fun getCharacter(id: Int) = characterService.getCharacter(id)


    override fun getCharacterList(): LiveData<Resource<Response<CharactersContainer>>> {
        return liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))
            try {
                emit(Resource.success(data = getCharacterContainer()))
            } catch (exception: Exception) {
                emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
            }
        }
    }

    override fun getCharacterById(id: Int): LiveData<Resource<Response<Character>>> {
        return liveData(Dispatchers.IO) {
            emit(Resource.loading(data = null))
            try {
                emit(Resource.success(data = getCharacter(id)))
            } catch (exception: Exception) {
                emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
            }
        }
    }
}