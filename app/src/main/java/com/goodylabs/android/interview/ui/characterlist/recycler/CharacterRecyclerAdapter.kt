package com.goodylabs.android.interview.ui.characterlist.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.goodylabs.android.interview.data.models.Character
import com.goodylabs.android.interview.databinding.ViewHolderCharacterBinding

class CharacterRecyclerAdapter(private val listener: CharacterItemListener) :
    RecyclerView.Adapter<CharacterViewHolder>() {

    private var items = listOf<Character>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding: ViewHolderCharacterBinding = ViewHolderCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding, listener)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun updateList(newList: List<Character>) {
        val diffCallback = DiffCallback(newList, items)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        items = newList
        diffResult.dispatchUpdatesTo(this)
    }

    interface CharacterItemListener {
        fun onItemClicked(character: Character)
    }
}