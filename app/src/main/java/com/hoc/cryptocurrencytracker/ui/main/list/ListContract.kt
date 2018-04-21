package com.hoc.cryptocurrencytracker.ui.main.list

import com.hoc.cryptocurrencytracker.base.BasePresenter
import com.hoc.cryptocurrencytracker.base.BaseView
import com.hoc.cryptocurrencytracker.data.ModelTicker

/**
 * Created by Peter Hoc on 13/03/2018.
 */

interface ListContract {
    interface View : BaseView {
        fun updateListTickers(tickers: List<ModelTicker>, isFirstLoad: Boolean)
        fun setSwipeRefreshing(refreshing: Boolean)
        fun showMessage(charSequence: CharSequence)
        fun navigateToDetailActivity(viewTicker: ModelTicker.Ticker?, position: Int)
        fun onLoadingItemInserted()
        fun onLoadingItemRemoved()
    }

    interface Presenter : BasePresenter<View> {
        fun loadMoreData()
        fun refreshData()
        fun onClickItemAt(position: Int)
    }
}