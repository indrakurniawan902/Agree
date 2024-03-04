package com.agree.ecosystem.monitoring.presentation.menu.detailarea

import androidx.recyclerview.widget.RecyclerView
import com.agree.ecosystem.core.utils.R
import com.agree.ecosystem.core.utils.base.AgrFragment
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.core.utils.utility.extension.getAttrsValue
import com.agree.ecosystem.core.utils.utility.navigation.navigation
import com.agree.ecosystem.monitoring.databinding.FragmentMonitoringDetailBinding
import com.agree.ecosystem.monitoring.databinding.LayoutEmptyListSubVesselInsideBinding
import com.agree.ecosystem.monitoring.domain.reqres.model.subvessel.SubVessel
import com.agree.ecosystem.monitoring.domain.reqres.model.vessel.Vessel
import com.agree.ecosystem.monitoring.presentation.base.activity.detailsubvassel.DetailSubVesselActivity
import com.agree.ecosystem.monitoring.presentation.navigation.MonitoringNavigation
import com.agree.ecosystem.utilities.domain.reqres.model.subsector.SubSector
import com.agree.ui.UiKitAttrs
import com.agree.ui.UiKitDimens
import com.agree.ui.snackbar.errorSnackBar
import com.devbase.presentation.recyclerview.endless.OnLoadMoreListener
import com.jakewharton.rxbinding3.widget.textChanges
import com.oratakashi.viewbinding.core.tools.startActivity
import com.telkom.legion.component.viewstate.ViewState
import com.telkom.legion.component.viewstate.showDefaultLayout
import com.telkom.legion.component.viewstate.showEmptyLayout
import com.telkom.legion.component.viewstate.showLoadingLayout
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class MonitoringDetailFragment :
    AgrFragment<FragmentMonitoringDetailBinding>(),
    MonitoringDetailDataContract,
    OnLoadMoreListener {

    /**
     * [query] is used to search sub vessel
     */
    private var query = ""

    private var vesselId: String = ""

    /**
     * List of sub vessel selected from filter
     */
    private var subSectors = arrayListOf<SubSector>()

    /**
     * [hasSmartFarming] is used to check if the sub vessel has smart farming
     */
    private var hasSmartFarming = false

    private var isRequestingData = false
    private var isLoadMoreFinish = false
    private val menuNav: MonitoringNavigation by navigation { activity }

    override fun initData() {
        super.initData()
        vesselId = activity?.intent?.getStringExtra("id") ?: ""
    }

    private val adapter: MonitoringDetailAdapter by lazy {
        MonitoringDetailAdapter { subVessel ->
            subVessel?.let { data ->
                activity?.startActivity(DetailSubVesselActivity::class.java) {
                    it.putExtra("id", data.id)
                    it.putExtra("sectorId", data.sectorId)
                }
            }
        }
    }

    override fun initUI() {
        with(binding) {
            rvSubArea.adapter = adapter
            toolbar.setBackButtonOnClick {
                requireActivity().onBackPressed()
            }
            with(nestedScrollView) {
                viewTreeObserver.addOnScrollChangedListener {
                    val view = getChildAt(childCount - 1)
                    if (view.bottom - (height + scrollY) == 0 && !isRequestingData && !isLoadMoreFinish) {
                        fetchLoadMoreSubVessel()
                    }
                }
            }
        }
    }

    override fun initAction() {
        super.initAction()
        with(binding) {
            cvFilter.setOnClickListener {
                showSectorDialog()
            }
            etSearch.editText?.let {
                it.textChanges()
                    .skipInitialValue()
                    .debounce(800, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { text ->
                        query = text.toString()
                        fetchListSubVessel()
                    }
            }
        }
    }

    private fun showSectorDialog() {
        com.agree.ecosystem.utilities.presentation.menu.sectorsdialog.SectorsDialogFragment.setup {
            selectedSectors = subSectors.joinToString(", ") {
                it.getFullSectorName()
            }
            isSmartFarming = true
            hasSmartFarming = this@MonitoringDetailFragment.hasSmartFarming

            setCallbackListener { data, isChecked ->
                subSectors.clear()
                subSectors.addAll(data)
                this@MonitoringDetailFragment.hasSmartFarming = isChecked
                addChips()
            }
        }.show(childFragmentManager, "dialog")
    }

    override fun onResume() {
        super.onResume()
        fetchListSubVessel()
    }

    private fun setFilterButtonColor(isFilter: Boolean) {
        with(binding) {
            ivFilter.setColorFilter(
                requireContext().getAttrsValue(if (isFilter) UiKitAttrs.colorPrimary else UiKitAttrs.black_900),
                android.graphics.PorterDuff.Mode.SRC_IN
            )
            cvFilter.strokeColor =
                requireContext().getAttrsValue(if (isFilter) UiKitAttrs.colorPrimary else UiKitAttrs.black_600)
            cvFilter.strokeWidth =
                resources.getDimension(if (isFilter) UiKitDimens.dimen_2dp else UiKitDimens.dimen_1dp)
                    .toInt()
        }
    }

    override fun initObserver() {
        super.initObserver()
        addObservers(MonitoringDetailObserver(this, viewModel))
        fetchListSubVessel()
        fetchVessel()
    }

    private fun addChips() {
        with(binding) {

            cgSubVessel.addAll(
                subSectors.map { it.getFullSectorName() }
                    .toMutableList()
                    .apply {
                        if (!hasSmartFarming) return@apply

                        add(0, getString(R.string.label_smartfarming))
                    }
            )

            cgSubVessel.setOnCloseIconClickListener { chipName ->
                if (chipName == getString(R.string.label_smartfarming)) {
                    hasSmartFarming = false
                } else {
                    subSectors.removeAt(subSectors.indexOfLast { it.getFullSectorName() == chipName })
                }
                fetchListSubVessel()
            }

            fetchListSubVessel()
        }
    }

    override fun fetchListSubVessel() {
        if (!isRequestingData) {
            viewModel.getListSubVessel(
                vesselId,
                search = query,
                filter = subSectors.joinToString(",") { it.id },
                hasSmartFarming
            )
            isRequestingData = true
            isLoadMoreFinish = false
        }
        setFilterButtonColor(subSectors.isNotEmpty() || hasSmartFarming)
    }

    override fun fetchVessel() {
        viewModel.getDetailVessel(vesselId)
    }

    override fun fetchLoadMoreSubVessel() {
        isRequestingData = true
        viewModel.loadMoreSubVessel(
            vesselId,
            search = query,
            filter = subSectors.joinToString(",") { it.id },
            hasSmartFarming
        )
    }

    override fun onGetDetailVesselSuccess(data: Vessel) {
        with(binding) {
            msvDetailVessel.showDefaultLayout()
            tvIdVessel.text = "ID Area ${data.code}"
            tvNameVessel.text = data.name
            tvLocation.text = data.districtName
            tvVesselWide.text = "${data.size} ha"
            tvTotalVesselWide.text = "${data.realizationSize} ha"
            tvRemain.text = "${data.size - data.realizationSize} ha"
        }
    }

    override fun onGetDetailVesselLoading() {
        binding.msvDetailVessel.showLoadingLayout()
    }

    override fun onGetDetailVesselEmpty() {
        // Do Nothing
    }

    override fun onGetDetailVesselFailed(e: Throwable?) {
        errorSnackBar(e?.message.toString())
    }

    override fun onGetListSubVesselSuccess(data: List<SubVessel>) {
        with(binding) {
            msvSubArea.showDefaultLayout()
            adapter.apply {
                setLoadMoreListener(this@MonitoringDetailFragment)
                setEndlessScroll(rvSubArea)
                resetEndlessScroll()
                adapter.clear()
                adapter.addOrUpdateAll(data)
            }
        }
        isRequestingData = false
    }

    override fun onGetListSubVesselLoading() {
        binding.msvSubArea.showLoadingLayout()
    }

    override fun onGetListSubVesselEmpty() {
        adapter.clear()
        binding.msvSubArea.showEmptyLayout()
        with(binding) {
            msvSubArea.getView(ViewState.EMPTY)?.let {
                val emptyBinding = LayoutEmptyListSubVesselInsideBinding.bind(it)
                with(emptyBinding) {
                    btnRegister.setOnClickListener {
                        menuNav.fromListSubVesselToCompanies()
                    }
                }
            }
        }
        isRequestingData = false
    }

    override fun onGetListSubVesselFailed(e: Throwable?) {
        errorSnackBar(e?.message.toString())
        isRequestingData = false
    }

    override fun onLoadMoreSubVesselSuccess(data: List<SubVessel>) {
        if (adapter.itemCount > 0) {
            adapter.hideLoadMoreLoading()
        }
        adapter.addOrUpdateAll(data)
        isRequestingData = false
    }

    override fun onLoadMoreSubVesselLoading() {
        if (adapter.itemCount > 0) {
            adapter.showLoadMoreLoading()
        }
    }

    override fun onLoadMoreSubVesselEmpty() {
        adapter.finishLoadMore()
        isRequestingData = false
        isLoadMoreFinish = true
    }

    override fun onLoadMoreSubVesselFailed() {
        if (adapter.itemCount > 0) {
            adapter.showLoadMoreError()
        }
        isRequestingData = false
    }

    private val viewModel: MonitoringDetailViewModel by viewModel()

    override fun onLoadMore(skip: Int?, limit: Int?, totalItemsCount: Int?, view: RecyclerView?) {
        // Do Nothing
    }

    override fun onLoadMoreRetryButtonClicked(skip: Int?, limit: Int?) {
        adapter.hideLoadMoreLoading()
        fetchLoadMoreSubVessel()
    }
}
