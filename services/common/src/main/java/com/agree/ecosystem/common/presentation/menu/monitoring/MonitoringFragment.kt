package com.agree.ecosystem.common.presentation.menu.monitoring

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.agree.ecosystem.common.R
import com.agree.ecosystem.common.databinding.FragmentMonitoringBinding
import com.agree.ecosystem.common.presentation.menu.monitoring.subvessels.SubVesselFragment
import com.agree.ecosystem.common.presentation.menu.monitoring.vessels.VesselFragment
import com.agree.ecosystem.core.utils.base.AgrFragment
import com.devbase.utils.util.getStringResource
import com.google.android.material.tabs.TabLayoutMediator

class MonitoringFragment : AgrFragment<FragmentMonitoringBinding>() {

    private var mediator: TabLayoutMediator? = null

    override fun initUI() {
        super.initUI()
        setupViewPager()
    }

    private fun createViewPagerAdapter(): FragmentStateAdapter {
        return object : FragmentStateAdapter(childFragmentManager, lifecycle) {
            override fun getItemCount() = 2

            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> SubVesselFragment()
                    else -> VesselFragment()
                }
            }
        }
    }

    private fun setupViewPager() {
        binding.vpContent.apply {
            adapter = createViewPagerAdapter()
            mediator = TabLayoutMediator(binding.tlLayout, this) { tab, position ->
                val tabLabel = when (position) {
                    0 -> getStringResource(R.string.label_list_subvessel)
                    else -> getStringResource(R.string.label_list_vessel)
                }
                tab.text = tabLabel
            }
        }
        mediator?.attach()
    }

    private fun cleanMediator() {
        mediator?.detach()
        mediator = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        cleanMediator()
    }
}
