package com.example.fairmoneytestapp.ui.onboarding.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.fairmoneytestapp.ui.onboarding.fragments.FirstFragment
import com.example.fairmoneytestapp.ui.onboarding.fragments.FourthFragment
import com.example.fairmoneytestapp.ui.onboarding.fragments.SecondFragment
import com.example.fairmoneytestapp.ui.onboarding.fragments.ThirdFragment

class StartFromScratchPagerAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return when (position){
            0 -> {
                FirstFragment()
            }
            1 -> {
                SecondFragment()
            }
            2 -> {
                ThirdFragment()
            }

            else -> {
                return FourthFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 4
    }


    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> ""
            1 -> ""
            2 -> ""
            else -> {
                return ""
            }
        }
    }

}