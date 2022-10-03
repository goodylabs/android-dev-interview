package com.goodylabs.android.interview.ui.characterdetails

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import androidx.core.text.parseAsHtml
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.load
import com.goodylabs.android.interview.R
import com.goodylabs.android.interview.data.models.Character
import com.goodylabs.android.interview.databinding.FragmentCharacterDetailsBinding
import com.goodylabs.android.interview.util.formattedDate
import com.goodylabs.android.interview.util.getStatusColor
import com.goodylabs.android.interview.util.getStatusText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterDetailsFragment : Fragment(R.layout.fragment_character_details) {

    private val viewModel: CharacterDetailsViewModel by viewModels()

    private lateinit var binding: FragmentCharacterDetailsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCharacterDetailsBinding.bind(view)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.character.collect { character ->
                        if (character == null) {
                            return@collect
                        }

                        binding.bindCharacterDetails(character)
                    }
                }

                launch {
                    viewModel.hasError.collect { hasError ->
                        binding.setErrorVisibility(hasError)
                    }
                }

                launch {
                    viewModel.isLoading.collect { isLoading ->
                        binding.setLoadingVisibility(isLoading)
                    }
                }
            }
        }
    }

    private fun FragmentCharacterDetailsBinding.setLoadingVisibility(isLoading: Boolean) {
        if (isLoading) {
            characterDetailsProgressBar.show()
        } else {
            characterDetailsProgressBar.hide()
        }
    }

    private fun FragmentCharacterDetailsBinding.setErrorVisibility(hasError: Boolean) {
        characterDetailsError.isVisible = hasError
        if (characterDetailsContainer.isVisible && hasError) {
            characterDetailsContainer.isVisible = false
        }
    }

    private fun FragmentCharacterDetailsBinding.bindCharacterDetails(character: Character) {
        characterDetailsContainer.isVisible = true
        characterDetailsName.text = getString(
            R.string.character_link,
            character.url,
            character.name
        ).parseAsHtml()
        characterDetailsName.movementMethod = LinkMovementMethod.getInstance()
        characterDetailsSpecies.text = character.species
        characterDetailsGender.text = character.gender
        characterDetailsStatus.text = character.status.getStatusText(requireContext())
        characterDetailsStatus.setTextColor(character.status.getStatusColor(requireContext()))
        characterDetailsDateCreated.text = character.created.formattedDate
        characterDetailsAvatar.load(character.image)
        characterDetailsLocation.text = character.origin.name
        characterDetailsLastLocation.text = character.location.name
    }
}
