package com.hoc.cryptocurrencytracker.di

import com.hoc.cryptocurrencytracker.ui.main.list.ListFragment
import com.hoc.cryptocurrencytracker.ui.main.search.SearchFragment
import dagger.Component
import javax.inject.Scope

/**
 * Created by Peter Hoc on 15/03/2018.
 */

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class MainActivityScope

@MainActivityScope
@Component(dependencies = [AppComponent::class], modules = [MainActivityModule::class])
interface MainActivityComponent {
    fun inject(listFragment: ListFragment)
    fun inject(searchFragment: SearchFragment)
}