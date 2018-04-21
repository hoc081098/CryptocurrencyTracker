package com.hoc.cryptocurrencytracker.ui.main.search

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hoc.cryptocurrencytracker.R
import com.hoc.cryptocurrencytracker.data.ModelTicker
import com.hoc.cryptocurrencytracker.ui.main.list.ModelTickerDiffCallback
import io.reactivex.Flowable
import io.reactivex.processors.PublishProcessor

class SearchAdapter : ListAdapter<ModelTicker, SearchAdapter.SearchItemViewHolder>(diffCallback) {
    private val publishProcessor = PublishProcessor.create<ModelTicker.Ticker>()
    val clickFlowable: Flowable<ModelTicker.Ticker> = publishProcessor

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_item, parent, false)
        return SearchItemViewHolder(view, publishProcessor)
    }

    override fun onBindViewHolder(holder: SearchItemViewHolder, position: Int) {
        holder.bind(getItem(position) as? ModelTicker.Ticker)
    }

    class SearchItemViewHolder(itemView: View, private val clickFlowable: PublishProcessor<ModelTicker.Ticker>) : RecyclerView.ViewHolder(itemView) {
        val searchItemName: TextView = itemView.findViewById(R.id.search_item_name)
        fun bind(item: ModelTicker.Ticker?) {
            if (item === null) return
            searchItemName.text = item.name ?: ""
            itemView.setOnClickListener { clickFlowable.onNext(item) }
        }
    }

    companion object {
        val diffCallback = ModelTickerDiffCallback()
    }
}