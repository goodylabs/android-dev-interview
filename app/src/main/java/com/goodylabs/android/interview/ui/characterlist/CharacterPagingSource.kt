package com.goodylabs.android.interview.ui.characterlist

import android.net.Uri
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.goodylabs.android.interview.data.Result
import com.goodylabs.android.interview.data.models.Character
import com.goodylabs.android.interview.data.repositories.CharacterRepository

class CharacterPagingSource(
    private val repository: CharacterRepository
) : PagingSource<Int, Character>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val nextPageNumber = params.key ?: 1
        val response = repository.getCharacterContainer(nextPageNumber)
        return when (response) {
            is Result.Success -> LoadResult.Page(
                data = response.data.results ?: emptyList(),
                prevKey = getPageKey(response.data.info?.prev),
                nextKey = getPageKey(response.data.info?.next)
            )
            is Result.Error -> {
                Log.d("CharacterPagingSource", response.message.toString())
                LoadResult.Error(response.message ?: Exception("Unable to load items"))
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    private fun getPageKey(pageUrl: String?): Int? {
        return pageUrl?.let { Uri.parse(it).getQueryParameter("page")?.toInt() }
    }
}
