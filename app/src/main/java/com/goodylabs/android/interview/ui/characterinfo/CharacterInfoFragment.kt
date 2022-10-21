package com.goodylabs.android.interview.ui.characterinfo

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.goodylabs.android.interview.R
import com.goodylabs.android.interview.databinding.FragmentCharacterInfoBinding
import com.goodylabs.android.interview.util.DateFormatter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterInfoFragment : Fragment() {

    private lateinit var binding: FragmentCharacterInfoBinding

    private val viewModel: CharacterInfoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCharacterInfo(arguments?.get("id") as Int)

        val observer = Observer<CharacterInfoViewModel.UiState> {
            binding.apply {
                Glide.with(requireContext())
                    .load(it.avatar)
                    .override(450, 450)
                    .into(characterAvatar)
                characterName.text = it.name
                characterGender.text = it.gender
                characterSpecies.text = it.species
                characterStatus.text = it.status
                characterCreated.text = DateFormatter.format(it.created)
                characterLink.text = it.name
            }
        }

        viewModel.uiState.observe(viewLifecycleOwner, observer)

        binding.characterLink.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(viewModel.uiState.value?.url))
            startActivity(browserIntent)
        }
    }
}