package com.goodylabs.android.interview.ui.characterlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.goodylabs.android.interview.databinding.FragmentCharacterListBinding
import com.goodylabs.android.interview.ui.characterlist.list.CharacterListAdapter
import com.goodylabs.android.interview.util.ScrollReachedBottomListener
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CharacterListFragment : Fragment() {

    private val viewModel: CharacterListViewModel by viewModels()

    private lateinit var binding: FragmentCharacterListBinding

    private val adapter = CharacterListAdapter {
        viewModel.selectCharacter(it)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterListBinding.inflate(inflater, container, false)
        setupBinding()
        observeViewModel()
        return binding.root
    }

    private fun setupBinding() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@CharacterListFragment.adapter
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
            addOnScrollListener(ScrollReachedBottomListener {
                viewModel.tryToLoadMore()
            })
        }
    }

    private fun observeViewModel() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            adapter.dataset = state.characters
        }
    }

}