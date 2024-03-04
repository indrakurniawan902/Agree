package com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel

import android.graphics.drawable.ColorDrawable
import androidx.core.text.HtmlCompat
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.agree.ecosystem.core.utils.base.AgrFragment
import com.agree.ecosystem.core.utils.utility.AppConfig
import com.agree.ecosystem.core.utils.utility.DialogUtils
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.core.utils.utility.extension.getAttrsValue
import com.agree.ecosystem.core.utils.utility.navigation.navigation
import com.agree.ecosystem.monitoring.R
import com.agree.ecosystem.monitoring.databinding.FragmentDetailSubVesselBinding
import com.agree.ecosystem.monitoring.domain.reqres.model.subvesseldetails.DetailSubVessel
import com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.activities.ActivitiesDataContract
import com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.activities.ActivitiesFragment
import com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.incident.IncidentDataContract
import com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.incident.IncidentFragment
import com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.shared.subvessel.SubVesselObserver
import com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.shared.subvessel.SubVesselViewModel
import com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.transaction.TransactionDataContract
import com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.transaction.TransactionFragment
import com.agree.ecosystem.monitoring.presentation.navigation.DetailSubVesselNavigation
import com.agree.ecosystem.monitoring.utils.Constant
import com.agree.ecosystem.monitoring.utils.MonitoringRecordType
import com.agree.ui.UiKitAttrs
import com.agree.ui.snackbar.errorSnackBar
import com.agree.ui.snackbar.normalSnackBar
import com.agree.ui.snackbar.successSnackBar
import com.devbase.presentation.viewpager.setup
import com.devbase.utils.ext.gone
import com.devbase.utils.ext.invisible
import com.devbase.utils.ext.visible
import com.devbase.utils.util.getDrawableResource
import com.google.android.material.tabs.TabLayout
import com.telkom.legion.component.viewstate.showDefaultLayout
import com.telkom.legion.component.viewstate.showLoadingLayout
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailSubVesselFragment :
    AgrFragment<FragmentDetailSubVesselBinding>(),
    DetailSubVesselDataContract {

    private var sectorId: String? = null
    private var subVesselId: String? = null

    private var activitiesContract: ActivitiesDataContract? = null
    private var transactionContract: TransactionDataContract? = null
    private var incidentContract: IncidentDataContract? = null

    override fun onResume() {
        super.onResume()
        initUI()
    }

    override fun initUI() {
        super.initUI()
        with(binding) {
            sectorId?.let {
                runCatching {
                    toolbar.text = when (it.toInt()) {
                        1 -> {
                            sharedVm.setSubVesselName(getString(R.string.name_sub_vessel_agriculture))
                            getString(R.string.title_sub_vessel_detail_agriculture)
                        }

                        2 -> {
                            sharedVm.setSubVesselName(getString(R.string.name_sub_vessel_fishery))
                            getString(R.string.title_sub_vessel_detail_fishery)
                        }

                        else -> {
                            sharedVm.setSubVesselName(getString(R.string.name_sub_vessel_livestock))
                            getString(R.string.title_sub_vessel_detail_livestock)
                        }
                    }
                }
            }

//            tabSubVessel.gone()
            vpSubVessel.setup {
                withFragment(this@DetailSubVesselFragment)
                withListFragment(
                    listOf(
                        ActivitiesFragment().apply { activitiesContract = this },
                        TransactionFragment().apply { transactionContract = this },
                        IncidentFragment().apply { incidentContract = this }
                    )
                )
                bindWithTabLayout(tabSubVessel) {
                    withListTitles(
                        listOf(
                            getString(R.string.label_activities),
                            getString(R.string.label_transaction),
                            getString(R.string.label_incident)
                        )
                    )
                    withListIcon(
                        listOf(
                            getDrawableResource(R.drawable.ic_activities) ?: ColorDrawable(),
                            getDrawableResource(R.drawable.ic_transaction) ?: ColorDrawable(),
                            getDrawableResource(R.drawable.ic_incident) ?: ColorDrawable()
                        )
                    )
                }
            }
            vpSubVessel.isUserInputEnabled = false

            tabSubVessel.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    sharedVm.setTabLayoutSelected(tab)
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {}
                override fun onTabReselected(tab: TabLayout.Tab) {}
            })

            tvMore.setOnClickListener { normalSnackBar("Under Development") }
        }
    }

    override fun initData() {
        super.initData()
        sectorId = activity?.intent?.getStringExtra("sectorId") ?: "1"
        viewModel.getDetailSubVessel(activity?.intent?.getStringExtra("id").orEmpty())
    }

    override fun initObserver() {
        super.initObserver()
        addObservers(
            DetailSubVesselObserver(this, viewModel),
            SubVesselObserver(this, sharedVm)
        )
        sharedVm.observeState()
        with(binding) {
            lifecycleScope.launch {
                // State for Bottom Action Bar
                sharedVm.isStateChanged.collectLatest {
                    val subVessel = sharedVm.getSubVessel()
                    if (
                        subVessel != null &&
                        !sharedVm.getActivitiesState()
                    ) {
                        bottomAppBarVisibility(subVessel.recordType)
                    } else {
                        bottomAppBar.invisible()
                    }
                    when (vpSubVessel.currentItem) {
                        0 -> {
                            if (
                                subVessel != null &&
                                !sharedVm.getActivitiesState()
                            ) {
                                bottomAppBarVisibility(subVessel.recordType)
                            } else {
                                bottomAppBar.invisible()
                            }
                        }

                        1 -> {
                            bottomAppBar.visible()
                        }

                        else -> {
                            bottomAppBar.visible()
                        }
                    }
                }
            }
        }
    }

    override fun initAction() {
        super.initAction()
        with(binding) {
            toolbar.setBackButtonOnClick { requireActivity().onBackPressed() }

            vpSubVessel.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    post = position
                    super.onPageSelected(position)
                    val subVessel = sharedVm.getSubVessel()
                    if (
                        subVessel != null &&
                        !sharedVm.getActivitiesState()
                    ) {
                        bottomAppBarVisibility(subVessel.recordType)
                    } else {
                        bottomAppBar.invisible()
                    }
                    when (position) {
                        0 -> {
                            if (
                                subVessel != null &&
                                !sharedVm.getActivitiesState()
                            ) {
                                bottomAppBarVisibility(subVessel.recordType)
                            } else {
                                bottomAppBar.invisible()
                            }
                        }

                        else -> {
                            if (
                                subVessel != null &&
                                !sharedVm.getActivitiesState()
                            ) {
                                bottomAppBar.visible()
                            } else {
                                bottomAppBar.invisible()
                            }
                        }
                    }
                    setButton(post)
                }
            })
            setButton(post)
        }
    }

    private fun setButton(post: Int) {
        binding.btnSharedAction.text = when (post) {
            0 -> getString(R.string.label_button_activities)
            1 -> getString(R.string.label_button_transaction)
            else -> getString(R.string.label_button_incident)
        }
    }

    private fun bottomAppBarVisibility(recordType: String) {
        with(binding) {
            if (recordType == MonitoringRecordType.INDIVIDUAL.value) {
                bottomAppBar.invisible()
            } else bottomAppBar.visible()
        }
    }

    override fun onGetDetailSubVesselLoading() {
        with(binding) {
            msvDetailSubVessel.showLoadingLayout()
            bottomAppBar.invisible()
        }
    }

    override fun onGetDetailSubVesselSuccess(data: DetailSubVessel) {
        with(binding) {
            msvDetailSubVessel.showDefaultLayout()

            // Update Shared ViewModel value
            sharedVm.setSubVesselId(data.id)
            sharedVm.setSubVessel(data)
            sharedVm.setStatusSubVessel(data.status)
            sharedVm.setCompanyId(data.companyId)
            sharedVm.setCommodityId(data.commodityId)

            subVesselId = sharedVm.getSubVesselId()

            // Shared Button Action Listener
            btnSharedAction.setOnClickListener {
                when (vpSubVessel.currentItem) {
                    0 -> activitiesContract?.onActionButtonTriggered(data)
                    1 -> transactionContract?.onActionButtonTriggered(data)
                    else -> incidentContract?.onActionButtonTriggered(data)
                }
            }

            val labelSubVessel =
                getString(R.string.label_sub_vessel_name, data.subVesselName, data.size)

            tvSubVesselName.text =
                HtmlCompat.fromHtml(labelSubVessel, HtmlCompat.FROM_HTML_MODE_LEGACY)
            tvProduction.text = data.bruto.ifEmpty { "-" }
            tvTarget.text = data.target.ifEmpty { "-" }
            tvRealization.text = data.netto.ifEmpty { "-" }
            tvCommodityName.text = data.commodityName

            // tvSubVesselName.text = data.subVesselName
            // tvSize.text = getString(R.string.label_size, data.size)
            val netto = if (data.netto.isEmpty()) 0.0 else data.netto.toDouble()
            val target = if (data.target.isEmpty()) 0.0 else data.target.toDouble()
            try {
                if (netto < target) {
                    tvRealization.setTextColor(UiKitAttrs.error_normal.getAttrsValue(requireContext()))
                } else {
                    tvRealization.setTextColor(UiKitAttrs.success_hover.getAttrsValue(requireContext()))
                }
            } catch (e: Exception) {
                tvTarget.text = "-"
                tvRealization.text = "-"
                tvProduction.text = "-"
            }

            if (data.hasSmartfarm) {
                btnSmartFarming.visible()
            } else {
                btnSmartFarming.gone()
            }

            btnSmartFarming.setOnClickListener {
                nav.fromDetailSubVesselToSmartFarming(data)
            }
        }
    }

    override fun onGetDetailSubVesselFailed(e: Throwable?) {
        with(binding) {
            e?.printStackTrace()
            if (AppConfig.isDebug) {
                errorSnackBar(e?.message.orEmpty())
            }
            bottomAppBar.invisible()
        }
    }

    override fun onStatusSubVesselChanged(status: String) {
        super.onStatusSubVesselChanged(status)
        with(binding) {
            if (status == Constant.STATUS_ACTIVE) {
                toolbar.inflateMenu(R.menu.menu_detail_subvessel)
                toolbar.setOnMenuItemClickListener {
                    if (it.itemId == R.id.menu_endcycle) {
                        showConfirmationDialog()
                    }
                    false
                }
                tvStatusSubVessel.apply {
                    text = getString(R.string.label_active)
                    setTextColor(UiKitAttrs.success_normal.getAttrsValue(context))
                }
                cvStatusSubVessel.apply {
                    setCardBackgroundColor(UiKitAttrs.colorPrimary100.getAttrsValue(context))
                }
            } else {
                toolbar.menu.clear()
                tvStatusSubVessel.apply {
                    text = getString(R.string.label_inactive)
                    setTextColor(UiKitAttrs.colorTertiary_300.getAttrsValue(context))
                }
                cvStatusSubVessel.apply {
                    setCardBackgroundColor(UiKitAttrs.colorTertiary_25.getAttrsValue(context))
                }
            }
        }
    }

    override fun doEndCycle() {
        subVesselId?.let {
            viewModel.endCycleSubVessel(it)
        }
    }

    override fun onEndCycleLoading() {
        onGetDetailSubVesselLoading()
    }

    override fun onEndCycleSuccess(data: DetailSubVessel) {
        sharedVm.setSubVesselId(data.id)
        viewModel.getDetailSubVessel(data.id)
    }

    override fun onEndCycleFailed(e: Throwable?) {
        requireActivity()
        errorSnackBar(e?.message.toString())
    }

    override fun showConfirmationDialog() {
        DialogUtils.showCustomDialog(
            context = requireContext(),
            title = getString(R.string.title_dialog_end_cycle),
            message = getString(R.string.label_dialog_end_cycle),
            positiveAction = Pair(getString(R.string.action_sure)) {
                doEndCycle()
                successSnackBar(getString(R.string.label_success_end_cycle))
            },
            negativeAction = Pair(getString(R.string.action_not), null),
            autoDismiss = false
        )
    }

    private var post: Int = 0
    private val viewModel: DetailSubVesselViewModel by viewModel()
    private val sharedVm: SubVesselViewModel by sharedViewModel()
    private val nav: DetailSubVesselNavigation by navigation { activity }
}
