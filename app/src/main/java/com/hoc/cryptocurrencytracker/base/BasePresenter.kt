package com.hoc.cryptocurrencytracker.base

import android.support.annotation.CallSuper

/**
 * Created by Peter Hoc on 13/03/2018.
 */

interface BasePresenter<V : BaseView> {
    var view: V?

    @CallSuper
    fun attachView(view: V?) {
        this.view = view
    }

    @CallSuper
    fun detachView() {
        view = null
    }

    fun subscribe() = Unit
    fun unsubscribe() = Unit
}