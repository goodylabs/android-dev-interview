package com.goodylabs.android.interview.ui.characterlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.goodylabs.android.interview.data.models.Character
import com.goodylabs.android.interview.databinding.CharacterItemBinding
import com.goodylabs.android.interview.ui.CustomClickInterface


class CharacterListAdapter(
    private var characters: List<Character>,
    private val customClickInterface: CustomClickInterface
) : RecyclerView.Adapter<CharacterListAdapter.CharacterListViewHolder>() {

    inner class CharacterListViewHolder(
        val binding: CharacterItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            apply {
                itemView.setOnClickListener {
                    customClickInterface.onClickListener(
                        characters[adapterPosition].id
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterListViewHolder {
        return CharacterListViewHolder(
            CharacterItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CharacterListViewHolder, position: Int) {
        holder.binding.apply {
            characterName.text = characters[position].name
            characterStatus.text = characters[position].status
            Glide.with(characterAvatar.context)
                .load(characters[position].image)
                .override(350, 350)
                .centerCrop()
                .into(characterAvatar)
        }
    }

    override fun getItemCount() = characters.size
}
