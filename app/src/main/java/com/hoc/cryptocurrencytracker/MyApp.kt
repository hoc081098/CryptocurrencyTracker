package com.hoc.cryptocurrencytracker

import android.app.Application
import android.support.v7.app.AppCompatActivity
import com.hoc.cryptocurrencytracker.di.*


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