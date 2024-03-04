package com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.incident

import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.agree.ecosystem.core.utils.base.AgrChildFragment
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.core.utils.utility.navigation.navigation
import com.agree.ecosystem.monitoring.R
import com.agree.ecosystem.monitoring.databinding.FragmentIncidentBinding
import com.agree.ecosystem.monitoring.databinding.LayoutEmptyCultivationActivityBinding
import com.agree.ecosystem.monitoring.domain.reqres.model.incident.Incident
import com.agree.ecosystem.monitoring.domain.reqres.model.subvesseldetails.DetailSubVessel
import com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.dialog.addactivity.AddActivityBottomSheet
import com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.incident.report.IncidentReportFragmentArgs
import com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.shared.subvessel.SubVesselObserver
import com.agree.ecosystem.monitoring.presentation.menu.detailsubvessel.shared.subvessel.SubVesselViewModel
import com.agree.ecosystem.monitoring.presentation.navigation.DetailSubVesselNavigation
import com.agree.ecosystem.monitoring.utils.Constant
import com.agree.ui.snackbar.errorSnackBar
import com.devbase.presentation.recyclerview.endless.OnLoadMoreListener
import com.telkom.legion.component.viewstate.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class IncidentFragment :
    AgrChildFragment<FragmentIncidentBinding>(),
    IncidentDataContract,
    OnLoadMoreListener {

    private var isRequestingData = false
    private var isLoadMoreFinish = false

    override fun initUI() {
        super.initUI()
        with(binding) {
            rvIncident.adapter = adapter
            rvIncident.isNestedScrollingEnabled = false
            with(root) {
                viewTreeObserver.addOnScrollChangedListener {
                    val view = getChildAt(childCount - 1)
                    if (view.bottom - (height + scrollY) == 0 && !isRequestingData && !isLoadMoreFinish) {
                        fetchLoadMoreIncident()
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        fetchIncidentList()
    }

    override fun initObserver() {
        super.initObserver()
        addObservers(IncidentObserver(this, viewModel))
        addObservers(SubVesselObserver(this, sharedVm))
    }

    override fun initAction() {
        super.initAction()
        with(binding) {
            msvIncident.setOnClickListener {
                menuDetailSubVessel.fromDetailSubVesselToIncidentReport(args.incident)
            }
        }
    }

    override fun fetchIncidentList() {
        if (!isRequestingData) {
            viewModel.getListIncident(sharedVm.getSubVesselId())
            isRequestingData = true
            isLoadMoreFinish = false
        }
    }

    override fun onIncidentLoading() {
        binding.msvIncident.showLoadingLayout()
    }

    override fun onIncidentSuccess(data: List<Incident>) {
        with(binding) {
            msvIncident.showDefaultLayout()
            adapter.apply {
                setLoadMoreListener(this@IncidentFragment)
                setEndlessScroll(rvIncident)
                resetEndlessScroll()
                clear()
                addOrUpdateAll(data)
            }
        }
        isRequestingData = false
    }

    override fun onIncidentFailed(e: Throwable?) {
        errorSnackBar(e?.message.toString())
        isRequestingData = false
        isLoadMoreFinish = true
    }

    override fun onIncidentEmpty() {
        with(binding) {
            if (sharedVm.getStatusSubVessel() == Constant.STATUS_ACTIVE) {
                msvIncident.showEmptyLayout()
                sharedVm.setActivitiesState(false)
            } else {
                msvIncident.showErrorLayout()
                sharedVm.setActivitiesState(true)
                msvIncident.getView(ViewState.ERROR)?.let {
                    with(LayoutEmptyCultivationActivityBinding.bind(it)) {
                        tvTitleEmpty.text = getString(R.string.label_inactive_sub_vessel, sharedVm.getSubVesselName())
                        tvDescEmpty.text = getString(
                            R.string.label_please_activate_livestock_to_add, sharedVm.getSubVesselName(),
                            getString(
                                R.string.label_incident_desc_inactive
                            )
                        )
                        btnRegister.text = getString(R.string.label_activate_the_sub_vessel_button, sharedVm.getSubVesselName())
                        btnRegister.setOnClickListener {
                            sharedVm.getSubVessel()?.let { subVessel ->
                                AddActivityBottomSheet(subVessel, callback = {
                                    sharedVm.setStatusSubVessel(Constant.STATUS_ACTIVE)
                                    sharedVm.setActivitiesState(true)
                                    fetchIncidentList()
                                }).showNow(childFragmentManager, "dialog")
                            }
                        }
                    }
                }
            }
        }
        isRequestingData = false
        isLoadMoreFinish = true
    }

    override fun fetchLoadMoreIncident() {
        isRequestingData = true
        viewModel.loadMoreListIncident(sharedVm.getSubVesselId())
    }

    override fun onLoadMoreIncidentLoading() {
        if (adapter.itemCount > 0) {
            adapter.showLoadMoreLoading()
        }
    }

    override fun onLoadMoreIncidentSuccess(data: List<Incident>) {
        if (adapter.itemCount > 0) {
            adapter.hideLoadMoreLoading()
        }
        adapter.addOrUpdateAll(data)
        isRequestingData = false
    }

    override fun onLoadMoreIncidentFailed(e: Throwable?) {
        if (adapter.itemCount > 0) {
            adapter.showLoadMoreError()
        }
        isRequestingData = false
    }

    override fun onLoadMoreIncidentEmpty() {
        adapter.finishLoadMore()
        isRequestingData = false
        isLoadMoreFinish = true
    }

    override fun onSubVesselIdChanged(id: String) {
        if (id.isNotEmpty()) {
            fetchIncidentList()
        }
    }

    override fun onActionButtonTriggered(data: DetailSubVessel) {
        menuDetailSubVessel.fromDetailSubVesselToAddIncident()
    }

    override fun onLoadMore(skip: Int?, limit: Int?, totalItemsCount: Int?, view: RecyclerView?) {
        // fetchLoadMoreIncident()
    }

    override fun onLoadMoreRetryButtonClicked(skip: Int?, limit: Int?) {
        adapter.hideLoadMoreLoading()
        fetchLoadMoreIncident()
    }

    private val adapter: IncidentAdapter by lazy {
        IncidentAdapter {
            menuDetailSubVessel.fromDetailSubVesselToIncidentReport(it)
        }
    }
    private val sharedVm: SubVesselViewModel by sharedViewModel()
    private val menuDetailSubVessel: DetailSubVesselNavigation by navigation { activity }
    private val viewModel: IncidentViewModel by viewModel()
    private val args: IncidentReportFragmentArgs by navArgs()
}
