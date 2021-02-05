package com.example.fairmoneytestapp.ui.onboarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.fairmoneytestapp.R
import com.example.fairmoneytestapp.ui.onboarding.adapter.StartFromScratchPagerAdapter
import com.example.fairmoneytestapp.utils.CustomViewPager
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingScreen : AppCompatActivity() {

    var tabLayout: TabLayout? = null
    var viewPager: CustomViewPager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_screen)

        viewPager = findViewById(R.id.viewpager_main)
        val tabLayout: TabLayout = findViewById(R.id.viewpagertab)
//        viewPager!!.setPagingEnabled(false)

        val fragmentAdapter = StartFromScratchPagerAdapter(supportFragmentManager)
        viewPager!!.adapter = fragmentAdapter

        tabLayout.setupWithViewPager(viewPager)

        viewPager!!.addOnPageChangeListener(
            TabLayout.TabLayoutOnPageChangeListener(
                tabLayout
            )
        )

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager!!.setCurrentItem(tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}