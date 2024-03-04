package com.agree.ecosystem.monitoring.presentation.menu.phaseactivity.subphaseactivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.core.utils.utility.navigation.navigation
import com.agree.ecosystem.monitoring.R
import com.agree.ecosystem.monitoring.data.reqres.model.detailactivitysop.FormSchema
import com.agree.ecosystem.monitoring.databinding.FragmentSubPhaseActivityDialogBinding
import com.agree.ecosystem.monitoring.domain.reqres.model.insertactivitysop.InsertActivitySopBundleParams
import com.agree.ecosystem.monitoring.domain.reqres.model.insertactivitysop.NavToAdditionalActivitySopParams
import com.agree.ecosystem.monitoring.domain.reqres.model.phaseactivity.PhaseActivity
import com.agree.ecosystem.monitoring.domain.reqres.model.phaseactivity.SubPhaseActivity
import com.agree.ecosystem.monitoring.domain.reqres.model.subvesseldetails.DetailSubVessel
import com.agree.ecosystem.monitoring.presentation.base.activity.activitysop.ActivitySopActivity
import com.agree.ecosystem.monitoring.presentation.base.activity.detailsubvassel.DetailSubVesselActivity
import com.agree.ecosystem.monitoring.presentation.menu.phaseactivity.PhaseActivityBottomSheet
import com.agree.ecosystem.monitoring.presentation.navigation.ActivitySopNavigation
import com.agree.ecosystem.monitoring.presentation.navigation.DetailSubVesselNavigation
import com.agree.ui.snackbar.errorSnackBar
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.oratakashi.viewbinding.core.binding.fragment.viewBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SubPhaseActivityBottomSheet(
    private val args: PhaseActivity,
    private val parent: PhaseActivityBottomSheet,
    private val date: String = "",
    private val detailSubVessel: DetailSubVessel,
    private val callback: (PhaseActivity) -> Unit
) : BottomSheetDialogFragment(), SubPhaseActivityDataContract {
    private val binding: FragmentSubPhaseActivityDialogBinding by viewBinding()
    private val viewModel: SubPhaseActivityViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        with(binding) {
            tvTitle.text = args.phaseName
            rvItemDialogActivitySop.adapter = adapter
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAction()
        initObserver()
    }

    private fun initObserver() {
        addObservers(SubPhaseActivityObserver(this, viewModel))
        viewModel.getActivityType(args)
    }

    private fun initAction() {
        binding.ivClose.setOnClickListener { dismiss() }
        binding.ivBack.setOnClickListener { dismiss() }
    }

    override fun onGetSuccess(data: List<SubPhaseActivity>) {
        adapter.addOrUpdateAll(data)
    }

    private val adapter = SubPhaseActivityAdapter(onItemClicked = { subPhaseActivity ->
        dismiss()
        parent.dismiss()
        val idBundle = InsertActivitySopBundleParams(
            commodityId = detailSubVessel.commodityId,
            commodityVarietyId = detailSubVessel.commodityVarietyId,
            companyMemberId = detailSubVessel.companyMemberId,
            companyId = detailSubVessel.companyId,
            vesselId = detailSubVessel.vesselId,
            workerId = detailSubVessel.workerId,
            subVesselId = detailSubVessel.id,
            phaseActivityId = subPhaseActivity.phaseId
        )

        when (activity) {
            is ActivitySopActivity -> {
                menuActivitySopNav.fromActivityInfoToInsertAdditionalActivity(
                    navParams = NavToAdditionalActivitySopParams(
                        idBundleParams = idBundle,
                        phaseName = subPhaseActivity.phaseName,
                        date = date,
                        formSchema = subPhaseActivity.formSchema,
                        isInsert = true,
                        order = subPhaseActivity.order
                    )
                )
            }

            is DetailSubVesselActivity -> {
                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val currentDate = sdf.format(Date()).toString()
                if (subPhaseActivity.formSchema?.activeformSchema.isNullOrEmpty() || subPhaseActivity.formSchema?.formSchemaItem.isNullOrEmpty()) {
                    errorSnackBar(getString(R.string.error_list_activeformSchema))
                } else
                    detailSubVesselNav.fromDetailSubVesselToInsertAdditionalActivity(
                        navParams = NavToAdditionalActivitySopParams(
                            idBundleParams = idBundle,
                            phaseName = subPhaseActivity.phaseName,
                            formSchema = subPhaseActivity?.formSchema ?: FormSchema(
                                emptyList(),
                                emptyList()
                            ),
                            isInsert = true,
                            order = subPhaseActivity.order,
                            date = currentDate
                        )
                    )
            }
        }
    })
    private val menuActivitySopNav: ActivitySopNavigation by navigation { activity }
    private val detailSubVesselNav: DetailSubVesselNavigation by navigation { activity }
}
