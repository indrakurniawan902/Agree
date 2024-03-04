package com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.activities

import android.graphics.Paint
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.TextAppearanceSpan
import androidx.core.content.ContextCompat
import com.agree.ecosystem.core.utils.base.AgrChildFragment
import com.agree.ecosystem.core.utils.utility.ConverterDate
import com.agree.ecosystem.core.utils.utility.defaultErrorHandling
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.core.utils.utility.navigation.navigation
import com.agree.ecosystem.core.utils.utility.toString
import com.agree.ecosystem.monitoring.R
import com.agree.ecosystem.monitoring.databinding.FragmentActivitiesBinding
import com.agree.ecosystem.monitoring.databinding.LayoutEmptyCultivationActivityBinding
import com.agree.ecosystem.monitoring.domain.reqres.model.activitysop.ActivitySop
import com.agree.ecosystem.monitoring.domain.reqres.model.activitysop.ActivitySopItemToDetailActivityParams
import com.agree.ecosystem.monitoring.domain.reqres.model.insertactivitysop.NavToAdditionalActivitySopParams
import com.agree.ecosystem.monitoring.domain.reqres.model.subvesseldetails.DetailSubVessel
import com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.dialog.addactivity.AddActivityBottomSheet
import com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.shared.subvessel.SubVesselObserver
import com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.shared.subvessel.SubVesselViewModel
import com.agree.ecosystem.monitoring.presentation.menu.phaseactivity.PhaseActivityBottomSheet
import com.agree.ecosystem.monitoring.presentation.navigation.DetailSubVesselNavigation
import com.agree.ecosystem.monitoring.utils.Constant
import com.agree.ui.UiColors
import com.agree.ui.UiKitStyle
import com.devbase.utils.ext.invisible
import com.devbase.utils.util.getColorResource
import com.oratakashi.viewbinding.core.tools.gone
import com.oratakashi.viewbinding.core.tools.visible
import com.telkom.legion.component.viewstate.ViewState
import com.telkom.legion.component.viewstate.showDefaultLayout
import com.telkom.legion.component.viewstate.showEmptyLayout
import com.telkom.legion.component.viewstate.showErrorLayout
import com.telkom.legion.component.viewstate.showLoadingLayout
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate
import java.util.Date

class ActivitiesFragment :
    AgrChildFragment<FragmentActivitiesBinding>(),
    ActivitiesDataContract {

    /**
     * Adapter for Activities
     */
    private val activitiesAdapter: ActivitiesAdapter by lazy {
        ActivitiesAdapter {
            it?.let {
                sharedVm.subVessel.value?.let { subVessel ->
                    val activitySopItemToDetailActivityParams =
                        ActivitySopItemToDetailActivityParams(it, subVessel)
                    if (it.isAdditional) {
                        detailSubVesselNav.fromDetailSubVesselToInsertAdditionalActivity(
                            navParams = NavToAdditionalActivitySopParams(
                                activitySopItemToDetailActivityParams
                                    .getInsertActivitySopBundleParams(),
                                phaseName = it.name,
                                formSchema = null,
                                isInsert = false,
                                date = LocalDate.now().toString()
                            )
                        )
                    } else {
                        detailSubVesselNav.fromDetailSubVesselToDetailActivity(
                            subVessel.id,
                            activitySopItemToDetailActivityParams.getDetailActivityParams()
                        )
                    }
                }
            }
        }
    }

    override fun initUI() {
        super.initUI()
        with(binding) {
//            val currentDate = Date().toString(ConverterDate.SIMPLE_DAY_DATE)
            val currentDate = getString(R.string.label_activities)
            tvDate.text = SpannableString(
                StringBuilder().apply {
                    append(currentDate)
                    append(" ")
                    append(getString(R.string.label_today))
                }
            ).apply {
                setSpan(
                    TextAppearanceSpan(context, UiKitStyle.BodySmallSemiBold),
                    currentDate.length,
                    this.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                setSpan(
                    ForegroundColorSpan(
                        ContextCompat.getColor(
                            requireContext(),
                            UiColors.tertiary_600
                        )
                    ),
                    currentDate.length,
                    this.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }

            tvSeeAll.paintFlags = Paint.UNDERLINE_TEXT_FLAG
            rvActivities.adapter = activitiesAdapter
        }
    }

    override fun initObserver() {
        super.initObserver()
        addObservers(
            ActivitiesObserver(this, viewModel),
            SubVesselObserver(this, sharedVm)
        )
    }

    override fun initAction() {
        super.initAction()
        with(binding) {
            tvSeeAll.setOnClickListener {
                sharedVm.subVessel.value?.let { subVessel ->
                    detailSubVesselNav.fromDetailSubVesselToActivitySop(
                        subVessel.id,
                        subVessel
                    )
                }
            }
        }
    }

    override fun onActionButtonTriggered(data: DetailSubVessel) {
        runCatching {
            PhaseActivityBottomSheet(
                data,
                Date().toString(ConverterDate.SQL_DATE)
            ) {
                getActivities(data.id)
            }.showNow(requireActivity().supportFragmentManager, "dialog")
        }.onFailure {
            it.printStackTrace()
        }
    }

    override fun onResume() {
        super.onResume()
        if (sharedVm.getSubVesselId().isNotEmpty()) {
            getActivities(sharedVm.getSubVesselId())
        }
    }

    override fun onStatusSubVesselChanged(status: String) {
        super.onStatusSubVesselChanged(status)
        if (status.isNotEmpty()) {
            getActivities(sharedVm.getSubVesselId())
        }
    }

    override fun onSubVesselChanged(subVessel: DetailSubVessel?) {
        subVessel?.let {
            with(binding) {
                if (subVessel.status == Constant.STATUS_ACTIVE) {
                    tvSeeAll.visible()
                    tvDate.visible()
                } else {
                    tvSeeAll.gone()
                }
            }
        }
    }

    override fun getActivities(id: String) {
        with(binding) {
            viewModel.getActivities(
                id,
                Date().toString(ConverterDate.SQL_DATE),
                sharedVm.getSubVessel()?.recordType
            ) {
                sharedVm.setActivitiesState(true)
                msvActivities.showEmptyLayout()
                tvSeeAll.invisible()
                defaultErrorHandling(this, it.orEmpty()) {
                    getActivities(id)
                }
            }
        }
    }

    override fun onActivityLoading() {
        binding.msvActivities.showLoadingLayout()
    }

    override fun onActivitySuccess(data: List<ActivitySop>) {
        with(binding) {
            sharedVm.setActivitiesState(false)
            tvSeeAll.visible()
            tvDate.visible()
            root.setBackgroundColor(getColorResource(com.telkom.legion.component.R.color.white))
            msvActivities.showDefaultLayout()
            activitiesAdapter.addOrUpdateAll(data)
        }
    }

    override fun onActivityEmpty() {
        with(binding) {
            if (sharedVm.getStatusSubVessel() == Constant.STATUS_ACTIVE) {
                tvSeeAll.visible()
                tvDate.visible()
                msvActivities.showEmptyLayout()
                sharedVm.setActivitiesState(false)
            } else {
                tvSeeAll.invisible()
                tvDate.invisible()
                root.setBackgroundColor(getColorResource(com.telkom.legion.component.R.color.black_200))
                msvActivities.showErrorLayout()
                sharedVm.setActivitiesState(true)
                with(LayoutEmptyCultivationActivityBinding.bind(msvActivities.getView(ViewState.ERROR))) {
                    tvTitleEmpty.text = getString(
                        R.string.label_inactive_sub_vessel,
                        sharedVm.getSubVesselName()
                    )
                    tvDescEmpty.text = getString(
                        R.string.label_please_activate_livestock_to_add,
                        sharedVm.getSubVesselName(),
                        getString(
                            R.string.label_activate_desc_inactive
                        )
                    )
                    btnRegister.text = getString(
                        R.string.label_activate_the_sub_vessel_button,
                        sharedVm.getSubVesselName()
                    )
                    btnRegister.setOnClickListener {
                        sharedVm.getSubVessel()?.let { subVessel ->
                            AddActivityBottomSheet(subVessel, callback = {
                                sharedVm.setStatusSubVessel(Constant.STATUS_ACTIVE)
                            }).showNow(childFragmentManager, "dialog")
                        }
                    }
                }
            }
        }
    }

    private val viewModel: ActivitiesViewModel by viewModel()
    private val sharedVm: SubVesselViewModel by sharedViewModel()
    private val detailSubVesselNav: DetailSubVesselNavigation by navigation { activity }
}
