package com.goodylabs.android.interview.ui.characterdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.goodylabs.android.interview.data.models.Character
import com.goodylabs.android.interview.databinding.FragmentCharacterDetailBinding
import com.goodylabs.android.interview.util.Status
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@AndroidEntryPoint
class CharacterFragment : Fragment() {

    private val viewModel: CharacterViewModel by viewModels()
    private lateinit var binding: FragmentCharacterDetailBinding
    private val args: CharacterFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val characterId = args.characterId

        viewModel.idWasUpdated(characterId).observe(viewLifecycleOwner, { resource ->
            when (resource.status) {
                Status.SUCCESS -> {
                    binding.progressBarDetail.visibility = View.GONE
                    resource.data?.let { response ->
                        fillCharacterInfo(response.body())
                    }
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), resource.message, Toast.LENGTH_LONG).show()
                }
                Status.LOADING -> {
                    binding.progressBarDetail.visibility = View.VISIBLE
                }
            }
        })
    }


    private fun fillCharacterInfo(character: Character?) {
        character?.let {
            binding.apply {
                characterDetailName.text = it.name
                characterDetailStatus.append(it.status)
                characterDetailSpecies.append(it.species)
                characterDetailLocation.append(it.location.name)
                characterDetailGender.append(it.gender)
                characterDetailCreated.append(formatDate(it.created))
                characterDetailUrl.append(it.url)
            }
            Glide.with(binding.root)
                .load(it.image)
                .transform(CircleCrop())
                .into(binding.characterDetailImage)
        }
    }

    private fun formatDate(time: String): String {
        val inputFormatter: DateTimeFormatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
        val outputFormatter: DateTimeFormatter =
            DateTimeFormatter.ofPattern("dd MMMM yyy", Locale.ENGLISH)
        val date: LocalDate = LocalDate.parse(time, inputFormatter)
        return outputFormatter.format(date)
    }
}