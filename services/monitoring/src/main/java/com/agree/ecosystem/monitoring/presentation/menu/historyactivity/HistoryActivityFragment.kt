package com.agree.ecosystem.monitoring.presentation.menu.historyactivity

import com.agree.ecosystem.core.utils.base.AgrFragment
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.monitoring.R
import com.agree.ecosystem.monitoring.databinding.FragmentHistoryActivityBinding
import com.agree.ecosystem.monitoring.presentation.menu.historyactivity.historyfilter.HistoryCompletedFragment
import com.agree.ecosystem.monitoring.presentation.menu.historyactivity.historyfilter.HistoryFilterViewPagerAdapter
import com.agree.ecosystem.monitoring.presentation.menu.historyactivity.historyfilter.HistoryMissedFragment
import com.devbase.utils.util.getStringResource
import com.google.android.material.tabs.TabLayoutMediator
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryActivityFragment :
    AgrFragment<FragmentHistoryActivityBinding>(),
    HistoryActivityDataContract {

    override fun initObserver() {
        super.initObserver()
        addObservers(HistoryActivityObserver(this, viewModel))
    }

    override fun initUI() {
        super.initUI()
        with(binding) {
            viewPager.adapter = HistoryFilterViewPagerAdapter(
                requireActivity(),
                listOf(
                    HistoryCompletedFragment(),
                    HistoryMissedFragment()
                )
            )
            TabLayoutMediator(tabFilter, viewPager) { tab, position ->
                when (position) {
                    0 -> tab.text = getStringResource(R.string.tab_pass)
                    1 -> tab.text = getStringResource(R.string.tab_miss)
                }
            }.attach()
            if (requireActivity().intent.extras?.get("date") == "") viewPager.setCurrentItem(
                1,
                true
            )
        }
    }

    override fun initAction() {
        super.initAction()
        binding.toolbar.setBackButtonOnClick { requireActivity().onBackPressed() }
    }

    private val viewModel: HistoryActivityViewModel by viewModel()
}
