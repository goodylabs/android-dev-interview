package com.goodylabs.android.interview.ui.characterDetails

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.goodylabs.android.interview.data.models.Character
import com.goodylabs.android.interview.databinding.FragmentCharacterDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterDetailsFragment : Fragment() {

    companion object {
        const val CHARACTER_DETAILS_KEY = "characterDetailsKey"
    }

    private val viewModel: CharacterDetailsViewModel by viewModels()

    private lateinit var binding: FragmentCharacterDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeCharacters()
        notifyAboutData()
    }

    private fun observeCharacters() {
        viewModel.character.observe(this) {
            setCharacter(it)
        }
    }

    private fun notifyAboutData() {
        arguments
            ?.getInt(CHARACTER_DETAILS_KEY)
            ?.let { viewModel.getCharacter(it) }
    }

    private fun setCharacter(character: Character) {

        with(binding) {
            Glide.with(root)
                .load(character.image)
                .into(characterImage)

            characterName.text = character.name
            status.text = character.status
        }
    }
}