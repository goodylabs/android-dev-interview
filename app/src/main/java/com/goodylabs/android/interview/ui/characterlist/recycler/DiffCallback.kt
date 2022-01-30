package com.goodylabs.android.interview.ui.characterlist.recycler

import androidx.recyclerview.widget.DiffUtil
import com.goodylabs.android.interview.data.models.Character

class DiffCallback(
    var newList: List<Character>,
    var oldList: List<Character>
) :
    DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}