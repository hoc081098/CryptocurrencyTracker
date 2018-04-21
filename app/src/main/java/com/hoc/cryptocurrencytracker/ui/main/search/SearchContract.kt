package com.hoc.cryptocurrencytracker.ui.main.search

import com.hoc.cryptocurrencytracker.base.BasePresenter
import com.hoc.cryptocurrencytracker.base.BaseView
import com.hoc.cryptocurrencytracker.data.ModelTicker
import io.reactivex.Flowable

interface SearchContract {
    interface View : BaseView {
        fun getSearchFlowable(): Flowable<String>
        fun showMessage(charSequence: CharSequence)
        fun showNoResultsFound()
        fun showSearchList(results: List<ModelTicker>)
        fun navigateToDetailActivity(ticker: ModelTicker.Ticker?)
        fun getClickItemFlowable(): Flowable<ModelTicker.Ticker>
    }

    interface Presenter : BasePresenter<SearchContract.View>
}