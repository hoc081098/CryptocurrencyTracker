package com.hoc.cryptocurrencytracker.util

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ConnectionUtil @Inject constructor(val application: Application) {
    fun isConnected(): Boolean {
        val manager = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = manager.activeNetworkInfo
        return networkInfo !== null && networkInfo.isConnected
    }
}