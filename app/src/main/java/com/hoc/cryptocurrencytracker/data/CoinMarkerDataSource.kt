package com.hoc.cryptocurrencytracker.data

import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * Created by Peter Hoc on 12/03/2018.
 */

interface CoinMarkerDataSource {
    fun getTickers(start: Int? = null, limit: Int? = null): Flowable<List<ModelTicker.Ticker>>
    fun deleteAllAndInsert(tickers: Array<ModelTicker.Ticker>): Completable
    fun insert(tickers: Array<ModelTicker.Ticker>): Completable
}

