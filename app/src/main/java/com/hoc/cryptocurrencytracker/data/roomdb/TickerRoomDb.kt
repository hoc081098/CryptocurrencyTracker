package com.hoc.cryptocurrencytracker.data.roomdb

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.hoc.cryptocurrencytracker.data.ModelTicker

/**
 * Created by Peter Hoc on 16/03/2018.
 */

@Database(entities = [ModelTicker.Ticker::class], version = 1, exportSchema = false)
abstract class TickerRoomDb : RoomDatabase() {
    abstract fun tickersDao(): TickersDao

    companion object {
        const val DB_NAME = "TickerDb"
    }
}