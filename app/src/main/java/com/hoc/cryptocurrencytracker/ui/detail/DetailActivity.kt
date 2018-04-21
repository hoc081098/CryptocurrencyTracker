package com.hoc.cryptocurrencytracker.ui.detail

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.hoc.cryptocurrencytracker.R
import com.hoc.cryptocurrencytracker.data.ModelTicker
import com.hoc.cryptocurrencytracker.util.toast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*

/**
 * Created by Peter Hoc on 16/03/2018.
 */

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)

        val ticker = intent.getParcelableExtra<ModelTicker.Ticker>(ITEM)
        Log.d(TAG, "Ticker: $ticker")
        bind(ticker)

        add_favorite_fab.setOnClickListener {
            //TODO: save favorites
            toast("click fab")
        }
    }

    private fun bind(ticker: ModelTicker.Ticker?) {
        collapsinglayout.run {
            setExpandedTitleTextAppearance(R.style.CollapsingToolbar_Expanded)
            setCollapsedTitleTextAppearance(R.style.CollapsingToolbar_Collapsed)
            title = getString(R.string.symbol_name,
                    ticker?.symbol ?: "?", ticker?.name ?: "?").toUpperCase()
        }

        Picasso.get().load("https://res.cloudinary.com/dxi90ksom/image/upload/${ticker?.symbol}.png")
                .fit()
                .centerCrop()
                .noFade()
                .error(R.drawable.ic_image_black_24dp)
                .placeholder(R.drawable.ic_image_black_24dp)
                .into(imageView)

        text_price_btc.text = getString(R.string.price_btc_str, ticker?.priceBtc ?: "?")
        text_price_usd.text = getString(R.string.price_usd_str, ticker?.priceUsd ?: "?")
        text_4h_volume_usd.text = getString(R.string._4h_volume_USD, ticker?._24hVolumeUsd ?: "?")
        text_market_cap_usd.text = getString(R.string.market_cap_usd, ticker?.marketCapUsd ?: "?")

        val redColor = ContextCompat.getColor(this, android.R.color.holo_red_dark)
        val greenColor = ContextCompat.getColor(this, android.R.color.holo_green_dark)

        val percentChange1h = ticker?.percentChange1h ?: "?"
        text_1h.text = getString(R.string.text_1h, percentChange1h)
        if ('-' in percentChange1h) {
            text_1h.setTextColor(redColor)
            image_1h.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp)
        } else {
            text_1h.setTextColor(greenColor)
            image_1h.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp)
        }
        val percentChange24h = ticker?.percentChange24h ?: "?"
        text_24h.text = getString(R.string.text_24h, percentChange24h)
        if ('-' in percentChange24h) {
            text_24h.setTextColor(redColor)
            image_24h.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp)
        } else {
            text_24h.setTextColor(greenColor)
            image_24h.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp)
        }
        val percentChange7d = ticker?.percentChange7d ?: "?"
        text_7d.text = getString(R.string.text_7d, percentChange1h)
        if ('-' in percentChange7d) {
            text_7d.setTextColor(redColor)
            image_7d.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp)
        } else {
            text_7d.setTextColor(greenColor)
            image_7d.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp)
        }
    }


    companion object {
        const val ITEM = "Item"
        private const val TAG = "DetailActivity"
    }
}