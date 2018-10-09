package com.hoc.cryptocurrencytracker

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import com.hoc.cryptocurrencytracker.di.AppComponent
import com.hoc.cryptocurrencytracker.di.AppModule
import com.hoc.cryptocurrencytracker.di.DaggerAppComponent
import com.hoc.cryptocurrencytracker.di.NetworkModule
import com.hoc.cryptocurrencytracker.di.RoomModule

/**
 * Created by Peter Hoc on 13/03/2018.
 */

class MyApp : Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .networkModule(NetworkModule())
            .roomModule(RoomModule())
            .build()
    }
}

inline val AppCompatActivity.appComponent
    get() = (application as MyApp).appComponent