package com.hoc.cryptocurrencytracker.data.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.hoc.cryptocurrencytracker.data.ModelTicker
import io.reactivex.Flowable
import org.intellij.lang.annotations.Language

/**
 * Created by Peter Hoc on 16/03/2018.
 */

@Dao()
abstract class TickersDao {
    @Language("RoomSql")
    @Query(value = "SELECT * FROM tickers_table")
    abstract fun getAllTickers(): Flowable<List<ModelTicker.Ticker>>

    @Language("RoomSql")
    @Query("SELECT * FROM tickers_table WHERE id = :id LIMIT 1")
    abstract fun findTickersById(id: String): Flowable<ModelTicker.Ticker>

    @Delete
    abstract fun deleteTickers(vararg tickers: ModelTicker.Ticker)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract fun updateTicker(ticker: ModelTicker.Ticker)

    @Insert
    abstract fun insertTickers(vararg tickers: ModelTicker.Ticker)

    @Query("DELETE FROM tickers_table")
    abstract fun deleteAll()

    @Transaction
    open fun deleteAllAndInsert(vararg tickers: ModelTicker.Ticker) {
        deleteAll()
        insertTickers(*tickers)
    }

    fun getTickersDistinct() = getAllTickers().distinctUntilChanged()
}