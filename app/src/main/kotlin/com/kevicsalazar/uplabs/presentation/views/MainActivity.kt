package com.kevicsalazar.uplabs.presentation.views

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.kevicsalazar.uplabs.R
import com.kevicsalazar.uplabs.presentation.BaseActivity
import com.kevicsalazar.uplabs.presentation.BasePresenter
import com.kevicsalazar.uplabs.presentation.ActivityComponent
import com.kevicsalazar.uplabs.utils.SimplePagerAdapter
import com.kevicsalazar.uplabs.utils.extensions.browse
import com.kevicsalazar.uplabs.utils.extensions.withArguments
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(toolbar)
        title = ""

        val pagerAdapter = SimplePagerAdapter(supportFragmentManager)
        pagerAdapter.addFragment(PageFragment().withArguments("type" to "material"), "MATERIAL")
        pagerAdapter.addFragment(PageFragment().withArguments("type" to "ios"), "IOS")
        viewpager.adapter = pagerAdapter
        tabs.setupWithViewPager(viewpager)

    }

    override val layout: Int get() = R.layout.activity_main

    override val presenter: BasePresenter<*>? get() = null

    override fun setupComponent(component: ActivityComponent) = component.inject(this)

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_browser -> browse("https://www.uplabs.com/")
        }
        return super.onOptionsItemSelected(item)
    }

}
