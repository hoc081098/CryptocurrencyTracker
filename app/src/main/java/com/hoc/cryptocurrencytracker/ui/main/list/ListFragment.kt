package com.hoc.cryptocurrencytracker.ui.main.list

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.hoc.cryptocurrencytracker.R
import com.hoc.cryptocurrencytracker.data.ModelTicker
import com.hoc.cryptocurrencytracker.ui.detail.DetailActivity
import com.hoc.cryptocurrencytracker.ui.main.MainActivity
import kotlinx.android.synthetic.main.frament_list.*
import kotlinx.android.synthetic.main.ticker_item_layout.view.*
import javax.inject.Inject
import android.support.v4.util.Pair as AndroidPair

/**
 * A placeholder fragment containing a simple view.
 */

class ListFragment : Fragment(), ListContract.View {
    @Inject
    lateinit var presenter: ListContract.Presenter
    private lateinit var tickersAdapter: TickersAdapter
    private lateinit var scrollListener: EndlessRecyclerViewScrollListener

    override fun updateListTickers(tickers: List<ModelTicker>, isFirstLoad: Boolean) {
        scrollListener.resetState()
        tickersAdapter.submitList(tickers)
        if (isFirstLoad) {
            recycler.scheduleLayoutAnimation()
        }
    }

    override fun setSwipeRefreshing(refreshing: Boolean) {
        swipe_layout.isRefreshing = refreshing
    }

    override fun showMessage(charSequence: CharSequence) {
        Snackbar.make(view!!, charSequence, Snackbar.LENGTH_SHORT)
                .setAction("REFRESH") {
                    presenter.refreshData()
                }
                .setDuration(3_000)
                .show()
    }

    override fun navigateToDetailActivity(viewTicker: ModelTicker.Ticker?, position: Int) {
        if (viewTicker === null) return

        val intent = Intent(context, DetailActivity::class.java).apply {
            putExtra(DetailActivity.ITEM, viewTicker)
        }
        val itemView = recycler.findViewHolderForAdapterPosition(position).itemView
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity!!,
                AndroidPair(itemView.image_symbol as View, getString(R.string.image_symbol_transition)),
                AndroidPair(itemView.text_price_usd as View, getString(R.string.text_price_usd_transition)),
                AndroidPair(itemView.text_price_btc as View, getString(R.string.text_price_btc_transition)),
                AndroidPair(itemView.text_percent_change_1h as View, getString(R.string.text_change1h_transition)),
                AndroidPair(itemView.text_percent_change_24h as View, getString(R.string.text_change24h_transition)),
                AndroidPair(itemView.text_percent_change_7d as View, getString(R.string.text_change7d_transition)),
                AndroidPair(itemView.image_percent_change_1h as View, getString(R.string.image_change1h_transition)),
                AndroidPair(itemView.image_percent_change_24h as View, getString(R.string.image_change24h_transition)),
                AndroidPair(itemView.image_percent_change_7d as View, getString(R.string.image_change7d_transition))
        ).toBundle()
        startActivity(intent, options)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).mainActivityComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frament_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler.run {
            setHasFixedSize(true)
            val linearLayoutManager = LinearLayoutManager(context)
            layoutManager = linearLayoutManager
            adapter = TickersAdapter(presenter::onClickItemAt).also {
                tickersAdapter = it
            }
            addOnScrollListener(EndlessRecyclerViewScrollListener(this, presenter::loadMoreData).also {
                scrollListener = it
            })
            layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down)
        }

        swipe_layout.run {
            setProgressBackgroundColorSchemeResource(R.color.colorPrimary)
            setColorSchemeResources(R.color.colorScheme1, R.color.colorScheme2)
            isRefreshing = true
            setOnRefreshListener(presenter::refreshData)
        }

        println("onViewCreated")
    }

    override fun onLoadingItemInserted() {
        tickersAdapter.notifyItemInserted(tickersAdapter.itemCount - 1)
    }

    override fun onLoadingItemRemoved() {
        tickersAdapter.notifyItemRemoved(tickersAdapter.itemCount)
    }

    override fun onResume() {
        super.onResume()
        presenter.attachView(this)
        presenter.subscribe()
        println("onResume")
    }

    override fun onPause() {
        super.onPause()
        presenter.unsubscribe()
        presenter.detachView()
        println("onPause")
    }
}
