package com.goodylabs.android.interview.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.goodylabs.android.interview.databinding.FragmentCharacterDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailsFragment: Fragment() {

    private val viewModel: CharacterDetailsViewModel by viewModels()

    private lateinit var binding: FragmentCharacterDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterDetailsBinding.inflate(inflater, container, false)
        setupBinding()
        return binding.root
    }

    private fun setupBinding() {
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

}