package com.agree.ecosystem.monitoring.presentation.menu.detailactivitysop.summary

import androidx.navigation.fragment.navArgs
import com.agree.ecosystem.core.utils.base.AgrFragment
import com.agree.ecosystem.monitoring.databinding.FragmentActivitySummaryBinding

class ActivitySummaryFragment : AgrFragment<FragmentActivitySummaryBinding>() {

    override fun initUI() {
        super.initUI()
        with(binding) {
            toolbar.text = args.activityName
            toolbar.setBackButtonOnClick {
                requireActivity().onBackPressed()
            }
            rvSummary.adapter = adapter
            adapter.addOrUpdateAll(args.formItems?.toList())
        }
    }

    private val args: ActivitySummaryFragmentArgs by navArgs()
    private val adapter: ActivitySummaryAdapter by lazy { ActivitySummaryAdapter() }
}
