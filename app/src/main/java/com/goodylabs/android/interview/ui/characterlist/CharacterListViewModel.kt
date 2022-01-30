package com.goodylabs.android.interview.ui.characterlist

import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import androidx.navigation.NavController
import com.goodylabs.android.interview.R
import com.goodylabs.android.interview.data.repositories.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.goodylabs.android.interview.data.models.Character
import com.goodylabs.android.interview.ui.characterDetails.CharacterDetailsFragment
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@HiltViewModel
class CharacterListViewModel @Inject constructor(private val characterRepository: CharacterRepository) :
    ViewModel() {

    private val _characters by lazy {
        MutableLiveData<List<Character>>()
            .also { getCharacters(it) }
    }

    val characters: LiveData<List<Character>> by lazy {
        _characters
    }

    private fun getCharacters(liveData: MutableLiveData<List<Character>>) {
        val job: Job = viewModelScope.launch {
            liveData.value = characterRepository.getCharacterContainer().results
        }
    }

    fun onCharacterClick(character: Character, navController: NavController?) {
        val bundle = bundleOf(Pair(CharacterDetailsFragment.CHARACTER_DETAILS_KEY, character.id))
        navController?.navigate(R.id.action_characterListFragment_to_characterDetailsFragment, bundle)
    }
}