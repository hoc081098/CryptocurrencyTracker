package com.hoc.cryptocurrencytracker.data

import io.reactivex.Flowable
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Peter Hoc on 11/03/2018.
 */


object CoinMarketCapApi {

    interface CoinMarketCapService {
        @GET("ticker/")
        fun getTickers(
                @Query("start") start: Int? = null,
                @Query("limit") limit: Int? = null
        ): Flowable<List<ModelTicker.Ticker>>
    }

    fun getCoinMarketCapService(retrofit: Retrofit): CoinMarketCapService =
            retrofit.create(CoinMarketCapService::class.java)

    const val BASE_URL = "https://api.coinmarketcap.com/v1/"
}