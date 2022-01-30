package com.goodylabs.android.interview.ui.characterlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.goodylabs.android.interview.R
import com.goodylabs.android.interview.databinding.FragmentCharacterListBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CharacterListFragment : Fragment() {

    private val viewModel: CharacterListViewModel by viewModels()
    @Inject
    lateinit var characterAdapter: CharacterAdapter

    private lateinit var binding: FragmentCharacterListBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()
        observeCharacters()

    }

    private fun observeCharacters() {
        viewModel.characters.observe(this) {
            characterAdapter.setCharacters(it)
        }
    }

    private fun initRecycler() {
        with(binding.recyclerView) {
            setHasFixedSize(true)
            adapter = characterAdapter
        }
        characterAdapter.setOnCharacterClickListener { viewModel.onCharacterClick(it, getNavController()) }
    }

    private fun getSupportFragmentManager() =
        activity?.supportFragmentManager

    private fun getNavController() = getSupportFragmentManager()
        ?.findFragmentById(R.id.main_fragment_container)
        ?.findNavController()
}