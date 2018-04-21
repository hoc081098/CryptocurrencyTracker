package com.hoc.cryptocurrencytracker.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

sealed class ModelTicker {
    object Loading : ModelTicker()

    @Entity(tableName = "tickers_table")
    @Parcelize
    data class Ticker constructor(
            @PrimaryKey(autoGenerate = false)
            @Json(name = "id")
            @ColumnInfo(name = "id")
            var id: String,

            @Json(name = "name")
            @ColumnInfo(name = "name")
            var name: String? = null,

            @Json(name = "symbol")
            @ColumnInfo(name = "symbol")
            var symbol: String? = null,

            @Json(name = "rank")
            @ColumnInfo(name = "rank")
            var rank: String? = null,

            @Json(name = "price_usd")
            @ColumnInfo(name = "price_usd")
            var priceUsd: String? = null,

            @Json(name = "price_btc")
            @ColumnInfo(name = "price_btc")
            var priceBtc: String? = null,

            @Json(name = "24h_volume_usd")
            @ColumnInfo(name = "24h_volume_usd")
            var _24hVolumeUsd: String? = null,

            @Json(name = "market_cap_usd")
            @ColumnInfo(name = "market_cap_usd")
            var marketCapUsd: String? = null,

            @Json(name = "available_supply")
            @ColumnInfo(name = "available_supply")
            var availableSupply: String? = null,

            @Json(name = "total_supply")
            @ColumnInfo(name = "total_supply")
            var totalSupply: String? = null,

            @Json(name = "max_supply")
            @ColumnInfo(name = "max_supply")
            var maxSupply: String? = null,

            @Json(name = "percent_change_1h")
            @ColumnInfo(name = "percent_change_1h")
            var percentChange1h: String? = null,

            @Json(name = "percent_change_24h")
            @ColumnInfo(name = "percent_change_24h")
            var percentChange24h: String? = null,

            @Json(name = "percent_change_7d")
            @ColumnInfo(name = "percent_change_7d")
            var percentChange7d: String? = null,

            @Json(name = "last_updated")
            @ColumnInfo(name = "last_updated")
            var lastUpdated: String? = null
    ) : ModelTicker(), Parcelable
}