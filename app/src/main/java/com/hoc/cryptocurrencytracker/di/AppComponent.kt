package com.hoc.cryptocurrencytracker.di

import com.hoc.cryptocurrencytracker.data.CoinMarkerDataSource
import com.hoc.cryptocurrencytracker.util.ConnectionUtil
import com.hoc.cryptocurrencytracker.util.SchedulerProvider
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Peter Hoc on 15/03/2018.
 */

@Singleton
@Component(modules = [AppModule::class, RoomModule::class, NetworkModule::class, CoinMarkerModule::class])
interface AppComponent {
    fun coinMarkerCapRepository(): CoinMarkerDataSource
    fun schedulerProvider(): SchedulerProvider
    fun connectionUtil(): ConnectionUtil
}