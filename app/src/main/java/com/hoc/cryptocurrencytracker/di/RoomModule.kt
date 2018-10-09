package com.hoc.cryptocurrencytracker.di

import android.app.Application
import androidx.room.Room
import com.hoc.cryptocurrencytracker.data.roomdb.TickerRoomDb
import com.hoc.cryptocurrencytracker.data.roomdb.TickersDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Peter Hoc on 17/03/2018.
 */

@Module
class RoomModule {
    @Provides
    @Singleton
    fun provideTickerRoomDb(application: Application): TickerRoomDb {
        return Room.databaseBuilder(
            application,
            TickerRoomDb::class.java,
            TickerRoomDb.DB_NAME
        ).build()
    }

    @Provides
    fun provideTickerDao(db: TickerRoomDb): TickersDao {
        return db.tickersDao()
    }
}