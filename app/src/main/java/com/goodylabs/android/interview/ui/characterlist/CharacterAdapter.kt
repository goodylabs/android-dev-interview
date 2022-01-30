package com.goodylabs.android.interview.ui.characterlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.goodylabs.android.interview.R
import com.goodylabs.android.interview.data.models.Character
import com.goodylabs.android.interview.databinding.ItemCharacterBinding

class CharacterAdapter : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    private val characters by lazy { mutableListOf<Character>() }
    private var listener: ((Character) -> Unit)? = null

    fun setCharacters(characters: List<Character>) {
        if (characters.isNotEmpty()) {
            this.characters.clear()
        }

        this.characters.addAll(characters)
        notifyDataSetChanged()
    }

    fun setOnCharacterClickListener(listener: (Character) -> Unit) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_character, parent, false)

        return CharacterViewHolder(
            itemView
        )
    }

    override fun getItemCount(): Int = characters.size

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = characters[position]
        holder.bind(character, listener)
    }

    class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemCharacterBinding.bind(itemView)

        fun bind(
            character: Character,
            listener: ((Character) -> Unit)?
        ) {
            with(binding) {
                Glide.with(root)
                    .load(character.image)
                    .into(characterImage)

                characterName.text = character.name
                status.text = character.status
                listener?.let { root.setOnClickListener { it(character) } }
            }
        }
    }
}