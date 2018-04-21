package com.hoc.cryptocurrencytracker.util

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Peter Hoc on 17/03/2018.
 */

interface SchedulerProvider {
    fun mainSchedulers(): Scheduler
    fun ioSchedulers(): Scheduler
}

class SchedulerProviderImpl @Inject constructor() : SchedulerProvider {
    override fun mainSchedulers(): Scheduler {
        return AndroidSchedulers.mainThread()

    }

    override fun ioSchedulers(): Scheduler {
        return Schedulers.io()
    }
}