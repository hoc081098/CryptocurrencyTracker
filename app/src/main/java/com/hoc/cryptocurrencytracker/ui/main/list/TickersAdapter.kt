package com.hoc.cryptocurrencytracker.ui.main.list

import android.graphics.Color
import android.graphics.PorterDuff
import android.support.v4.content.ContextCompat
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hoc.cryptocurrencytracker.R
import com.hoc.cryptocurrencytracker.data.ModelTicker
import com.squareup.picasso.Picasso
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.load_more_item.view.*
import kotlinx.android.synthetic.main.ticker_item_layout.view.*

/**
 * Created by Peter Hoc on 11/03/2018.
 */

class ModelTickerDiffCallback : DiffUtil.ItemCallback<ModelTicker>() {
    override fun areItemsTheSame(oldItem: ModelTicker?, newItem: ModelTicker?): Boolean {
        return if (oldItem is ModelTicker.Ticker && newItem is ModelTicker.Ticker)
            oldItem.id == newItem.id
        else oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: ModelTicker?, newItem: ModelTicker?): Boolean {
        return if (oldItem is ModelTicker.Ticker && newItem is ModelTicker.Ticker)
            oldItem == newItem
        else oldItem === newItem
    }
}

class TickersAdapter(private val onClickListener: (position: Int) -> Unit)
    : ListAdapter<ModelTicker, RecyclerView.ViewHolder>(diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutId = when (viewType) {
            LOAD_MORE_TYPE -> R.layout.load_more_item
            VIEW_TICKER_TYPE -> R.layout.ticker_item_layout
            else -> throw IllegalStateException("Don't know type")
        }
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return when (viewType) {
            LOAD_MORE_TYPE -> LoadMoreViewHolder(view)
            VIEW_TICKER_TYPE -> TickerViewHolder(view, onClickListener)
            else -> throw IllegalStateException("Don't know type")
        }
    }

    override fun getItemViewType(position: Int) = when (getItem(position)) {
        ModelTicker.Loading -> LOAD_MORE_TYPE
        is ModelTicker.Ticker -> VIEW_TICKER_TYPE
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TickerViewHolder -> holder.bindTo(getItem(position) as? ModelTicker.Ticker)
            is LoadMoreViewHolder -> holder.bindTo()
            else -> throw IllegalStateException()
        }
    }

    companion object {
        val diffCallback = ModelTickerDiffCallback()
        const val LOAD_MORE_TYPE = 0
        const val VIEW_TICKER_TYPE = 2
    }

    private class LoadMoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindTo() {
            itemView.progress_bar.run {
                indeterminateDrawable.setColorFilter(
                        ContextCompat.getColor(context, R.color.progressBackgroundColorScheme),
                        PorterDuff.Mode.SRC_IN)
                isIndeterminate = true
            }
        }
    }

    private class TickerViewHolder(override val containerView: View, val onClickListener: (position: Int) -> Unit)
        : RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bindTo(viewTicker: ModelTicker.Ticker?) = containerView.run {
            setOnClickListener {
                onClickListener(adapterPosition)
            }
            text_name.text = context.getString(R.string.view_ticker_name, viewTicker?.rank, viewTicker?.name)
            text_price_usd.text = context.getString(R.string.price_usd, viewTicker?.priceUsd)
            text_price_btc.text = context.getString(R.string.price_btc, viewTicker?.priceBtc)

            val greenTextColor = Color.parseColor("#2e7d32")
            val redTextColor = Color.parseColor("#d50000")

            text_percent_change_1h.run {
                text = context.getString(R.string.percent_change1h)
                setTextColor(if (viewTicker?.percentChange1h?.contains("-") == true) redTextColor else greenTextColor)
            }
            image_percent_change_1h.setImageResource(
                    if (viewTicker?.percentChange1h?.contains("-") == true) R.drawable.ic_arrow_drop_down_black_24dp
                    else R.drawable.ic_arrow_drop_up_black_24dp
            )


            text_percent_change_24h.run {
                text = context.getString(R.string.percent_change24h)
                setTextColor(if (viewTicker?.percentChange24h?.contains("-") == true) redTextColor else greenTextColor)
            }
            image_percent_change_24h.setImageResource(
                    if (viewTicker?.percentChange24h?.contains("-") == true) R.drawable.ic_arrow_drop_down_black_24dp
                    else R.drawable.ic_arrow_drop_up_black_24dp
            )

            text_percent_change_7d.run {
                text = context.getString(R.string.percent_change7d)
                setTextColor(if (viewTicker?.percentChange7d?.contains("-") == true) redTextColor else greenTextColor)

            }
            image_percent_change_7d.setImageResource(
                    if (viewTicker?.percentChange7d?.contains("-") == true) R.drawable.ic_arrow_drop_down_black_24dp
                    else R.drawable.ic_arrow_drop_up_black_24dp
            )


            Picasso.get().load("https://res.cloudinary.com/dxi90ksom/image/upload/${viewTicker?.symbol}.png")
                    .fit()
                    .centerCrop()
                    .error(R.drawable.ic_image_black_24dp)
                    .placeholder(R.drawable.ic_image_black_24dp)
                    .into(image_symbol)
        }
    }


}


