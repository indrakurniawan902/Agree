package com.agree.ecosystem.monitoring.presentation.menu.totalactivitysop

import com.agree.ecosystem.core.utils.base.AgrFragment
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.monitoring.R
import com.agree.ecosystem.monitoring.databinding.FragmentTotalActivityBinding
import com.agree.ecosystem.monitoring.databinding.LayoutErrorTotalActivitiesBinding
import com.agree.ecosystem.monitoring.domain.reqres.model.totalactivitysop.TotalActivitySop
import com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.shared.subvessel.SubVesselObserver
import com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.shared.subvessel.SubVesselViewModel
import com.telkom.legion.component.viewstate.ViewState
import com.telkom.legion.component.viewstate.showDefaultLayout
import com.telkom.legion.component.viewstate.showErrorLayout
import com.telkom.legion.component.viewstate.showLoadingLayout
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class TotalActivityFragment :
    AgrFragment<FragmentTotalActivityBinding>(),
    TotalActivityDataContract {

    override fun initData() {
        super.initData()
        sharedVm.setSubVesselId(requireActivity().intent.getStringExtra("subVesselId").orEmpty())
    }

    override fun initObserver() {
        super.initObserver()
        addObservers(
            TotalActivityObserver(this, viewModel),
            SubVesselObserver(this, sharedVm)
        )
    }

    override fun onSubVesselIdChanged(id: String) {
        super.onSubVesselIdChanged(id)
        if (id.isNotEmpty()) {
            getTotalActivities(id)
        }
    }

    override fun getTotalActivities(id: String) {
        with(binding) {
            viewModel.getTotalActivitySop(id) {
                msvTotalActivitySop.showErrorLayout()
                msvTotalActivitySop.getView(ViewState.ERROR)?.let {
                    with(LayoutErrorTotalActivitiesBinding.bind(it)) {
                        btnRetry.setOnClickListener { getTotalActivities(id) }
                    }
                }
            }
        }
    }

    override fun onGetTotalActivitySuccess(data: TotalActivitySop) {
        with(binding) {
            msvTotalActivitySop.showDefaultLayout()
            tvActivityMissed.text = data.activitiesMissed.toString()
            tvActivityDone.text = data.activitiesCompleted.toString()
            tvActivityDoneTotal.text = getString(R.string.label_slash).plus(data.totalActivities)
        }
    }

    override fun onGetTotalActivityLoading() {
        binding.msvTotalActivitySop.showLoadingLayout()
    }

    override fun onGetTotalActivityEmpty() {
        with(binding) {
            msvTotalActivitySop.showDefaultLayout()
            tvActivityMissed.text = getString(R.string.label_zero)
            tvActivityDone.text = getString(R.string.label_zero)
            tvActivityDoneTotal.text = getString(R.string.label_slash).plus(getString(R.string.label_zero))
        }
    }

    private val viewModel: TotalActivityViewModel by viewModel()
    private val sharedVm: SubVesselViewModel by sharedViewModel()
}
