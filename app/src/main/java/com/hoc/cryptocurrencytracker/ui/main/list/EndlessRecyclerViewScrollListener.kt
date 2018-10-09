package com.hoc.cryptocurrencytracker.ui.main.list

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class EndlessRecyclerViewScrollListener(
    private val layoutManager: RecyclerView.LayoutManager,
    private val onLoadMore: () -> Unit
) : androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
    private val visibleThreshold = when (layoutManager) {
        is LinearLayoutManager -> VISIBLE_THRESHOLD
        is GridLayoutManager -> VISIBLE_THRESHOLD * layoutManager.spanCount
        is StaggeredGridLayoutManager -> VISIBLE_THRESHOLD * layoutManager.spanCount
        else -> throw IllegalStateException()
    }
    private var loading = true

    override fun onScrolled(view: RecyclerView, dx: Int, dy: Int) {
        if (dy <= 0) return

        val totalItemCount = layoutManager.itemCount
        val lastVisibleItemPosition = when (layoutManager) {
            is StaggeredGridLayoutManager -> {
                val lastVisibleItemPositions = layoutManager.findLastVisibleItemPositions(null)
                lastVisibleItemPositions.max() ?: 0
            }
            is GridLayoutManager -> layoutManager.findLastVisibleItemPosition()
            is LinearLayoutManager -> layoutManager.findLastVisibleItemPosition()
            else -> throw IllegalStateException()
        }

        if (!loading && lastVisibleItemPosition + visibleThreshold >= totalItemCount) {
            onLoadMore()
            loading = true
        }
    }

    fun resetState() {
        loading = false
    }

    companion object {
        private const val VISIBLE_THRESHOLD = 5
    }
}