package com.agree.ecosystem.monitoring.presentation.menu.historyactivity.historyfilter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class HistoryFilterViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val fragmentList: List<Fragment>
) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }
}
