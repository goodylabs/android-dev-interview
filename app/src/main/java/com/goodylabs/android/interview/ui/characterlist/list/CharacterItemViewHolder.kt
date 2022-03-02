package com.goodylabs.android.interview.ui.characterlist.list

import androidx.recyclerview.widget.RecyclerView
import com.goodylabs.android.interview.data.models.Character
import com.goodylabs.android.interview.databinding.ViewholderCharacterItemBinding

class CharacterItemViewHolder(private val binding: ViewholderCharacterItemBinding): RecyclerView.ViewHolder(binding.root) {

    fun bind(character: Character, onSelect: (Character) -> Unit) {
        binding.character = character
        binding.root.setOnClickListener {
            onSelect(character)
        }
    }
    
}
