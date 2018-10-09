package com.hoc.cryptocurrencytracker.data

import com.hoc.cryptocurrencytracker.data.roomdb.TickersDao
import com.hoc.cryptocurrencytracker.util.ConnectionUtil
import com.hoc.cryptocurrencytracker.util.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class CoinMarkerRepository @Inject constructor(
    private val coinMarkerService: CoinMarketCapApi.CoinMarketCapService,
    private val dao: TickersDao,
    private val schedulerProvider: SchedulerProvider,
    private val connectionUtil: ConnectionUtil
) : CoinMarkerDataSource {
    override fun insert(tickers: Array<ModelTicker.Ticker>): Completable {
        return Completable.fromAction { dao.insertTickers(*tickers) }
            .subscribeOn(schedulerProvider.ioSchedulers())
    }

    override fun deleteAllAndInsert(tickers: Array<ModelTicker.Ticker>): Completable {
        return Completable.fromAction { dao.deleteAllAndInsert(*tickers) }
            .subscribeOn(schedulerProvider.ioSchedulers())
    }

    override fun getTickers(start: Int?, limit: Int?): Flowable<List<ModelTicker.Ticker>> {
        return Flowable.just(connectionUtil.isConnected())
            .flatMap { isConnected: Boolean ->
                if (isConnected) {
                    coinMarkerService
                        .getTickers(start = start, limit = limit)
                        .subscribeOn(schedulerProvider.ioSchedulers())
                } else {
                    dao.getTickersDistinct()
                        .subscribeOn(schedulerProvider.ioSchedulers())
                        .map {
                            val fromIndex = start ?: 0
                            it.subList(
                                fromIndex, minOf(
                                    (limit
                                        ?: 0) + fromIndex, it.size
                                )
                            )
                        }
                }
            }.map {
                it.sortedBy { it.rank?.toInt() }
            }
    }
}