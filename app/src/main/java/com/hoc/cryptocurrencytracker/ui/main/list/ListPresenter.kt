package com.hoc.cryptocurrencytracker.ui.main.list

import com.hoc.cryptocurrencytracker.data.CoinMarkerDataSource
import com.hoc.cryptocurrencytracker.data.ModelTicker
import com.hoc.cryptocurrencytracker.util.ConnectionUtil
import com.hoc.cryptocurrencytracker.util.SchedulerProvider
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

/**
 * Created by Peter Hoc on 13/03/2018.
 */

class ListPresenter @Inject constructor(
    private val repository: CoinMarkerDataSource,
    private val schedulerProvider: SchedulerProvider,
    private val connectionUtil: ConnectionUtil
) : ListContract.Presenter {
    override var view: ListContract.View? = null
    private val compositeDisposable = CompositeDisposable()
    private val models = mutableListOf<ModelTicker>()
    private var startIndex = 0

    override fun refreshData() {
        startIndex = 0
        getData()
    }

    override fun subscribe() {
        println("on subscribe")
        if (models.isEmpty()) {
            println("List is empty, need get data")
            getData()
        }
    }

    override fun unsubscribe() {
        compositeDisposable.clear()
    }

    private fun getData() {
        println("Get data")
        compositeDisposable += repository.getTickers(start = startIndex, limit = LIMIT)
            .doOnSubscribe {
                when (startIndex) {
                    0 -> view?.setSwipeRefreshing(true)
                    else -> {
                        models += ModelTicker.Loading
                        view?.onLoadingItemInserted()
                    }
                }
            }
            .map { it.toTypedArray() }
            .flatMap {
                when {
                    connectionUtil.isConnected() -> when (startIndex) {
                        0 -> repository.deleteAllAndInsert(it)
                        else -> repository.insert(it)
                    }
                    else -> Completable.complete()
                }.andThen(Flowable.just(it)).subscribeOn(schedulerProvider.ioSchedulers())
            }
            .observeOn(schedulerProvider.mainSchedulers())
            .subscribeBy(
                onError = {
                    view?.showMessage(it.message ?: "Unknown error")
                    view?.setSwipeRefreshing(false)
                },
                onNext = {
                    when (startIndex) {
                        0 -> models.clear()
                        else -> if (models.isNotEmpty() && models.last() === ModelTicker.Loading) {
                            models.removeAt(models.lastIndex)
                            view?.onLoadingItemRemoved()
                        }
                    }

                    view?.setSwipeRefreshing(false)
                    view?.updateListTickers(models.apply { addAll(it) }, startIndex == 0)
                    startIndex += it.size
                }
            )
    }

    override fun loadMoreData() {
        if (models.size >= MAX_LENGTH_LIST_DATA) {
            view?.showMessage("Got max items")
            return
        }
        getData()
    }

    override fun onClickItemAt(position: Int) {
        view?.navigateToDetailActivity(models[position] as? ModelTicker.Ticker, position)
    }

    private companion object {
        const val LIMIT = 20
        const val MAX_LENGTH_LIST_DATA = 20 * 5
    }
}
