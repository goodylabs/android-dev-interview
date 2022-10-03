package com.goodylabs.android.interview.ui.characterlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.goodylabs.android.interview.R
import com.goodylabs.android.interview.databinding.FragmentCharacterListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterListFragment : Fragment(R.layout.fragment_character_list) {

    private val viewModel: CharacterListViewModel by viewModels()

    private lateinit var binding: FragmentCharacterListBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCharacterListBinding.bind(view)

        val characterAdapter = CharacterListAdapter { id ->
            findNavController().navigate(
                CharacterListFragmentDirections
                    .actionCharacterListFragmentToCharacterDetailsFragment(id)
            )
        }
        binding.charactersRecycleView.adapter = characterAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.characters.collectLatest { pagingData ->
                        characterAdapter.submitData(pagingData)
                    }
                }
            }
        }
    }
}
