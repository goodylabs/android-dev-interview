package com.goodylabs.android.interview.ui.characterlist.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.goodylabs.android.interview.data.models.Character
import com.goodylabs.android.interview.databinding.ViewHolderCharacterBinding


class CharacterViewHolder(
    private val binding: ViewHolderCharacterBinding,
    private val listener: CharacterRecyclerAdapter.CharacterItemListener
) : RecyclerView.ViewHolder(binding.root),
    View.OnClickListener {

    private lateinit var character: Character

    fun bind(character: Character) {
        this.character = character
        binding.root.setOnClickListener(this)
        binding.characterName.text = character.name
        binding.characterStatus.text = character.status
        Glide.with(binding.root)
            .load(character.image)
            .into(binding.characterImage)
    }

    override fun onClick(v: View?) {
        listener.onItemClicked(character)
    }
}