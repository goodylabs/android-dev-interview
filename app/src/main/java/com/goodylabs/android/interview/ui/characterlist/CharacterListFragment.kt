package com.goodylabs.android.interview.ui.characterlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.goodylabs.android.interview.data.models.Character
import com.goodylabs.android.interview.databinding.FragmentCharacterListBinding
import com.goodylabs.android.interview.ui.CustomClickInterface
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterListFragment : Fragment(), CustomClickInterface {

    private val viewModel: CharacterListViewModel by viewModels()

    private lateinit var binding: FragmentCharacterListBinding

    private lateinit var characterListAdapter: CharacterListAdapter

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

        setupRecyclerView()

        val characterListObserver = Observer<MutableList<Character>> {
            characterListAdapter.notifyDataSetChanged()
        }

        viewModel.characterList.observe(viewLifecycleOwner, characterListObserver)

        collectErrors()
    }

    private fun setupRecyclerView() = binding.characterRecyclerView.apply {
        characterListAdapter = CharacterListAdapter(
            viewModel.characterList.value!!,
            this@CharacterListFragment
        )
        adapter = characterListAdapter
        layoutManager = LinearLayoutManager(context)
    }

    private fun collectErrors() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.error.collectLatest {
                    Toast.makeText(
                        context, it, Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onClickListener(id: Int) {
        val action =
            CharacterListFragmentDirections.actionCharacterListFragmentToCharacterInfoFragment(id)
        findNavController().navigate(action)
    }
}