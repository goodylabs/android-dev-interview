package com.goodylabs.android.interview.ui.characterlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.goodylabs.android.interview.R
import com.goodylabs.android.interview.data.models.Character
import com.goodylabs.android.interview.databinding.CharacterItemBinding
import com.goodylabs.android.interview.util.getStatusColor
import com.goodylabs.android.interview.util.getStatusText

class CharacterListAdapter(
    private val onClick: (characterId: Int) -> Unit
) : PagingDataAdapter<Character, CharacterListAdapter.ViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CharacterItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    inner class ViewHolder(
        private val binding: CharacterItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Character) {
            binding.bindCharacter(item)
        }

        private fun CharacterItemBinding.bindCharacter(item: Character) {
            val context = binding.root.context

            characterAvatar.load(item.image)
            characterContainer.setOnClickListener { onClick(item.id) }
            characterName.text = item.name
            characterSpeciesAndGender.text = context.getString(
                R.string.character_description,
                item.species,
                item.gender
            )

            characterStatus.text = item.status.getStatusText(context)
            characterStatus.setTextColor(item.status.getStatusColor(context))

            characterOrigin.text = item.origin.name
            characterLastLocation.text = item.location.name
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Character>() {
        override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
            return oldItem == newItem
        }
    }
}
