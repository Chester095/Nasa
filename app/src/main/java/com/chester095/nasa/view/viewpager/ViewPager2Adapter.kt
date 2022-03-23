package com.chester095.nasa.view.viewpager

import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


class ViewPager2Adapter(private val fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    private val fragments = arrayOf(EarthFragment(), MarsFragment(), SystemFragment())


    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int) = fragments[position]


}