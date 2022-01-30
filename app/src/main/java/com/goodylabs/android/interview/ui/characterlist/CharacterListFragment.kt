package com.goodylabs.android.interview.ui.characterlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.goodylabs.android.interview.data.models.Character
import com.goodylabs.android.interview.databinding.FragmentCharacterListBinding
import com.goodylabs.android.interview.ui.characterlist.recycler.CharacterRecyclerAdapter
import com.goodylabs.android.interview.util.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterListFragment : Fragment(), CharacterRecyclerAdapter.CharacterItemListener {

    private val viewModel: CharacterListViewModel by viewModels()
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
        val adapter = CharacterRecyclerAdapter(this)
        binding.characterRecycler.adapter = adapter

        viewModel.charactersLiveData.observe(viewLifecycleOwner, { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    binding.characterRecycler.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    resource.data?.let { response ->
                        adapter.updateList(response.body()!!.results)
                    }
                }
                Status.ERROR -> {
                    binding.characterRecycler.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(requireContext(), resource.message, Toast.LENGTH_LONG).show()
                }
                Status.LOADING -> {
                    binding.characterRecycler.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        })
    }

    override fun onItemClicked(character: Character) {
        val action = CharacterListFragmentDirections
                .actionCharacterListFragmentToCharacterFragment(character.id)
        Navigation.findNavController(requireView()).navigate(action)
    }
}