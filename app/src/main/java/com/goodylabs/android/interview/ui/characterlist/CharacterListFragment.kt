package com.goodylabs.android.interview.ui.characterlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.goodylabs.android.interview.R
import com.goodylabs.android.interview.data.models.CharacterInfo
import com.goodylabs.android.interview.databinding.FragmentCharacterListBinding
import com.goodylabs.android.interview.ui.adapters.CharacterRecyclerViewAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CharacterListFragment : Fragment() {

    private val viewModel: CharacterListViewModel by viewModels()

    private var _binding: FragmentCharacterListBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterListBinding.inflate(inflater, container, false)
        viewModel.getCharacters()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.liveEvent.observe(viewLifecycleOwner) {
            setUpCharacterRecyclerView(it.results ?: emptyList())
        }
    }

    private fun setUpCharacterRecyclerView(characterList: List<CharacterInfo>) {
        binding.characterRV.apply {
            adapter = CharacterRecyclerViewAdapter(
                characterList
            ) { character ->
                // pass the selected character to the details fragment
                val selectedCharacter = Bundle()
                selectedCharacter.putSerializable("selectedCharacter", character)

                findNavController().navigate(R.id.characterDetailFragment, selectedCharacter)
            }
            layoutManager = GridLayoutManager(requireContext(), 3)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}