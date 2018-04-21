package com.hoc.cryptocurrencytracker.di

import com.hoc.cryptocurrencytracker.ui.main.list.ListContract
import com.hoc.cryptocurrencytracker.ui.main.list.ListPresenter
import com.hoc.cryptocurrencytracker.ui.main.search.SearchContract
import com.hoc.cryptocurrencytracker.ui.main.search.SearchPresenter
import dagger.Binds
import dagger.Module

@Module
abstract class MainActivityModule {
    @MainActivityScope
    @Binds
    abstract fun provideListPresenter(listPresenter: ListPresenter): ListContract.Presenter

    @MainActivityScope
    @Binds
    abstract fun proviveSearchPresenter(searchPresenter: SearchPresenter): SearchContract.Presenter
}
