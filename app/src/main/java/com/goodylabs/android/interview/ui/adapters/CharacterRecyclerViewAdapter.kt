package com.goodylabs.android.interview.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.goodylabs.android.interview.data.models.CharacterInfo
import com.goodylabs.android.interview.databinding.RecyclerViewItemBinding
import com.goodylabs.android.interview.util.setImage

class CharacterRecyclerViewAdapter(
    private val listOfCharacters: List<CharacterInfo>,
    private val onClickAction: (selectedCharacter: CharacterInfo) -> Unit
): RecyclerView.Adapter<CharacterRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(
        private val itemListBinding: RecyclerViewItemBinding,
        private val onClickAction: (selectedCharacter: CharacterInfo) -> Unit
    ): RecyclerView.ViewHolder(itemListBinding.root) {
        fun bind(characterInfo: CharacterInfo) {
            with(itemListBinding) {
                characterNameTV.text = characterInfo.name
                statusTV.text = characterInfo.status
                characterIV.setImage(characterInfo.image)
                // I know you insisted on getting the data from the API again, but that seems
                // just pointless to me, we already have the data, we could just use it instead
                // of requesting it again (saves API quota, user's data usage and is easier)
                view.setOnClickListener { onClickAction(characterInfo) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onClickAction)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listOfCharacters[position])
    }

    override fun getItemCount(): Int {
        return listOfCharacters.size
    }
}