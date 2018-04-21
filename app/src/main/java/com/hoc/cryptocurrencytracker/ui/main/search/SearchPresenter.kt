package com.hoc.cryptocurrencytracker.ui.main.search

import com.hoc.cryptocurrencytracker.data.CoinMarkerDataSource
import com.hoc.cryptocurrencytracker.data.ModelTicker
import com.hoc.cryptocurrencytracker.util.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SearchPresenter @Inject constructor(
        private val repository: CoinMarkerDataSource,
        private val schedulerProvider: SchedulerProvider
) : SearchContract.Presenter {
    override var view: SearchContract.View? = null
    private val compositeDisposable = CompositeDisposable()

    private fun setupSearch() {
        view?.getSearchFlowable()?.let {
            compositeDisposable += it.throttleLast(300, TimeUnit.MILLISECONDS)
                    .debounce(200, TimeUnit.MILLISECONDS)
                    .distinctUntilChanged()
                    .filter { it.isNotBlank() }
                    .map { it.toLowerCase() }
                    .switchMap { query ->
                        repository.getTickers(start = 0)
                                .map {
                                    it.filterTo(mutableListOf<ModelTicker>()) {
                                        val name = it.name?.toLowerCase() ?: return@filterTo false
                                        name in query || query in name
                                    }
                                }
                                .subscribeOn(schedulerProvider.ioSchedulers())
                    }
                    .subscribeOn(schedulerProvider.ioSchedulers())
                    .observeOn(schedulerProvider.mainSchedulers())
                    .subscribeBy(
                            onError = {
                                view?.showMessage("Error: ${it.message}")
                            },
                            onNext = {
                                if (it.size > 0) {
                                    view?.showSearchList(it)
                                } else {
                                    view?.showNoResultsFound()
                                }
                            }
                    )

        }
    }

    override fun subscribe() {
        super.subscribe()
        setupSearch()

        view?.getClickItemFlowable()?.let {
            compositeDisposable += it.observeOn(schedulerProvider.mainSchedulers())
                    .subscribeBy {
                        view?.navigateToDetailActivity(it)
                    }
        }
    }

    override fun unsubscribe() {
        compositeDisposable.clear()
    }
}