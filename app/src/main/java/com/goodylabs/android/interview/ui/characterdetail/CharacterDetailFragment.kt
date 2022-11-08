package com.goodylabs.android.interview.ui.characterdetail

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.goodylabs.android.interview.data.models.CharacterInfo
import com.goodylabs.android.interview.databinding.FragmentDetailsBinding
import com.goodylabs.android.interview.util.formatToNormalDate
import com.goodylabs.android.interview.util.setImage

class CharacterDetailFragment: Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        // get data from our bundle
        loadCharacterDataIntoUi(getCharacterData())
        return binding.root
    }

    private fun getCharacterData(): CharacterInfo {
        return if (Build.VERSION.SDK_INT >= 33) {
            // this fragment cannot be created without the bundle being passed, that's why !! is used
            this.requireArguments().getSerializable("selectedCharacter", CharacterInfo::class.java)!!
        } else {
            this.requireArguments().getSerializable("selectedCharacter") as CharacterInfo
        }
    }

    private fun loadCharacterDataIntoUi(character: CharacterInfo) {
        with(binding) {
            numberOfEpisodesTV.text = character.episode.size.toString()
            statusTV.text = character.status
            idTV.text = character.id.toString()
            nameTV.text = character.name
            speciesTV.text = character.species
            genderTV.text = character.gender
            orignTV.text = character.origin.name
            locationTV.text = character.location.name
            creationDateTV.text = character.created.formatToNormalDate()
            pictureIV.setImage(character.image)

            nameTV.setOnClickListener {
                openInBrowser(character.id)
            }
            linkIV.setOnClickListener {
                openInBrowser(character.id) // just in case
            }
        }
    }

    private fun openInBrowser(id: Int) {
        val link = "https://rickandmortyapi.com/api/character/$id"
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
        browserIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(browserIntent)
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}