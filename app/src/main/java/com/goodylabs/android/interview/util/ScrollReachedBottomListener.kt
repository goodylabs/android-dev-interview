package com.goodylabs.android.interview.util

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ScrollReachedBottomListener(
    private val onReachedBottom: () -> Unit
): RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val layoutManager = recyclerView.layoutManager as? LinearLayoutManager
            ?: throw IllegalStateException("Recycler view should have LinearLayoutManager to use ScrollReachedBottomListener")

        if (layoutManager.itemCount <= layoutManager.findLastVisibleItemPosition() + 1) {
            onReachedBottom()
        }
    }
}