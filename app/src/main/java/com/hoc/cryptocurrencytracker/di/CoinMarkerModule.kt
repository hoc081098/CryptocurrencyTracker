package com.hoc.cryptocurrencytracker.di

import com.hoc.cryptocurrencytracker.data.CoinMarkerDataSource
import com.hoc.cryptocurrencytracker.data.CoinMarkerRepository
import com.hoc.cryptocurrencytracker.util.SchedulerProvider
import com.hoc.cryptocurrencytracker.util.SchedulerProviderImpl
import dagger.Binds
import dagger.Module

/**
 * Created by Peter Hoc on 20/03/2018.
 */
@Module
abstract class CoinMarkerModule {
    @Binds
    abstract fun provideCoinMarkerDataSource(coinMarkerRepository: CoinMarkerRepository): CoinMarkerDataSource

    @Binds
    abstract fun provideSchedulerProvider(schedulerProviderImpl: SchedulerProviderImpl): SchedulerProvider
}