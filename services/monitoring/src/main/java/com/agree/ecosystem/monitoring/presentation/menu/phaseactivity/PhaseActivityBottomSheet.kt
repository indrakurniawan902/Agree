package com.agree.ecosystem.monitoring.presentation.menu.phaseactivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.monitoring.databinding.FragmentPhaseActivityDialogBinding
import com.agree.ecosystem.monitoring.domain.reqres.model.phaseactivity.PhaseActivity
import com.agree.ecosystem.monitoring.domain.reqres.model.subvesseldetails.DetailSubVessel
import com.agree.ecosystem.monitoring.presentation.menu.phaseactivity.subphaseactivity.SubPhaseActivityBottomSheet
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.oratakashi.viewbinding.core.binding.fragment.viewBinding
import com.telkom.legion.component.viewstate.showDefaultLayout
import com.telkom.legion.component.viewstate.showEmptyLayout
import com.telkom.legion.component.viewstate.showLoadingLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

class PhaseActivityBottomSheet(
    private val detailSubVessel: DetailSubVessel,
    private val date: String = "",
    private val callback: (PhaseActivity) -> Unit = {}
) : BottomSheetDialogFragment(), PhaseActivityDataContract {

    private val binding: FragmentPhaseActivityDialogBinding by viewBinding()
    private val viewModel: PhaseActivityViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.rvItemDialogActivitySop.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAction()
        initObserver()
    }

    private fun initObserver() {
        addObservers(PhaseActivityObserver(this, viewModel))
        detailSubVessel.let {
            viewModel.getPhaseActivityList(it.id)
        }
    }

    private fun initAction() {
        binding.ivClose.setOnClickListener { dismiss() }
    }

    override fun onGetPhaseActivitySuccess(data: List<PhaseActivity>) {
        with(binding) {
            msvPhaseActivity.showDefaultLayout()
            adapter.addOrUpdateAll(data)
        }
    }

    override fun onGetPhaseActivityLoading() {
        binding.msvPhaseActivity.showLoadingLayout()
    }

    override fun onGetPhaseActivityEmpty() {
        binding.msvPhaseActivity.showEmptyLayout()
    }

    private val adapter = PhaseActivityAdapter(onItemClicked = {
        if (it.subPhaseActivityItems.isEmpty()) {
            this.dismiss()
        } else {
            SubPhaseActivityBottomSheet(it, this, date, detailSubVessel) {}.showNow(
                childFragmentManager,
                "dialog"
            )
        }
    })

    companion object {
        const val TAG = "PhaseActivityDialog"
    }
}
