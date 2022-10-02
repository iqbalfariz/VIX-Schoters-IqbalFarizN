package com.izo.newsapp.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment {
        val fragment = HomeFragment()
        val bundle = Bundle()
        if (position == 0) {
            bundle.putString(HomeFragment.ARG_TAB, HomeFragment.TAB_NEWS)
        } else {
            bundle.putString(HomeFragment.ARG_TAB, HomeFragment.TAB_BOOKMARK)
        }
        fragment.arguments = bundle
        return fragment
        return fragment
    }

    override fun getItemCount(): Int {
        return 2
    }
}