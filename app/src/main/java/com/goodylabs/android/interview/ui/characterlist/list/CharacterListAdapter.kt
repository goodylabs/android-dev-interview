package com.goodylabs.android.interview.ui.characterlist.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.goodylabs.android.interview.R
import com.goodylabs.android.interview.data.models.Character

class CharacterListAdapter(
    private val selectCharacter: (Character) -> Unit
): RecyclerView.Adapter<CharacterItemViewHolder>() {

    var dataset: List<Character> = emptyList()
        set(value) {
            DiffUtil.calculateDiff(CharacterListDiffCallback(oldList = field, newList = value))
                .dispatchUpdatesTo(this)
            field = value
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterItemViewHolder {
        return CharacterItemViewHolder(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.viewholder_character_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CharacterItemViewHolder, position: Int) {
        holder.bind(dataset[position], selectCharacter)
    }

    override fun getItemCount(): Int = dataset.size

}