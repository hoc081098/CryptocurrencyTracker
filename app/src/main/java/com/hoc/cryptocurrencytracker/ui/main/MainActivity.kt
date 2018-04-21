package com.hoc.cryptocurrencytracker.ui.main

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import com.hoc.cryptocurrencytracker.R
import com.hoc.cryptocurrencytracker.appComponent
import com.hoc.cryptocurrencytracker.di.DaggerMainActivityComponent
import com.hoc.cryptocurrencytracker.di.MainActivityComponent
import com.hoc.cryptocurrencytracker.ui.main.list.ListFragment
import com.hoc.cryptocurrencytracker.ui.main.search.SearchFragment
import com.hoc.cryptocurrencytracker.util.toast
import com.miguelcatalan.materialsearchview.MaterialSearchView
import io.reactivex.Flowable
import io.reactivex.processors.PublishProcessor
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    val mainActivityComponent: MainActivityComponent by lazy {
        DaggerMainActivityComponent.builder()
                .appComponent(appComponent)
                .build()
    }
    private lateinit var listFragment: ListFragment
    private lateinit var searchFragment: SearchFragment
    private val fragments = mutableListOf<Fragment>()
    private var doubleToExit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        if (savedInstanceState === null) {
            listFragment = ListFragment().also { fragments += it }
            searchFragment = SearchFragment().also { fragments += it }
            displayFragment(listFragment)
        }

        search_view.setOnSearchViewListener(object : MaterialSearchView.SearchViewListener {
            override fun onSearchViewClosed() {
                displayFragment(listFragment)
            }

            override fun onSearchViewShown() {
                displayFragment(searchFragment)
            }
        })
    }

    private fun displayFragment(fragment: Fragment) {
        println("display fragment: ${fragment::class.java.simpleName}")
        val transaction = supportFragmentManager.beginTransaction()
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)

        fragments.forEach {
            when (it) {
                fragment -> if (it.isAdded) {
                    transaction.show(it)
                } else {
                    transaction.add(R.id.main_container, it, it::class.java.simpleName)
                }
                else -> if (it.isAdded) {
                    transaction.hide(it)
                }
            }
        }

        transaction.commit()
    }

    fun getSearchFlowable(): Flowable<String> {
        val processor = PublishProcessor.create<String>()
        search_view.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = true

            override fun onQueryTextChange(newText: String?) = newText?.let {
                processor.onNext(it)
                true
            } ?: false
        })
        return processor.onBackpressureLatest()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        search_view.setMenuItem(menu?.findItem(R.id.action_search))
        return true
    }

    override fun onBackPressed() {
        if (searchFragment.isVisible) {
            if (search_view.isSearchOpen) {
                search_view.closeSearch()
            } else {
                displayFragment(listFragment)
            }
        } else {
            if (doubleToExit) {
                super.onBackPressed()
            }
            doubleToExit = true
            toast("Press back again to exit")
            Handler().postDelayed({
                doubleToExit = false
            }, 2_000)
        }
    }
}
