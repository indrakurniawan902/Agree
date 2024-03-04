package com.agree.ecosystem.common.presentation.menu.monitoring.vessels

import android.annotation.SuppressLint
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.agree.ecosystem.common.R
import com.agree.ecosystem.common.databinding.FragmentVesselBinding
import com.agree.ecosystem.common.databinding.LayoutEmptyListVesselBinding
import com.agree.ecosystem.common.databinding.LayoutLostConnectionVesselBinding
import com.agree.ecosystem.common.presentation.navigation.MenuNavigation
import com.agree.ecosystem.core.utils.base.AgrFragment
import com.agree.ecosystem.core.utils.domain.reqres.AgrPrefUsecase
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.core.utils.utility.extension.getAttrsValue
import com.agree.ecosystem.core.utils.utility.extension.setItemSeparator
import com.agree.ecosystem.core.utils.utility.navigation.navigation
import com.agree.ecosystem.core.utils.utility.offline.ConnectivityObserver
import com.agree.ecosystem.monitoring.domain.reqres.model.vessel.Vessel
import com.agree.ecosystem.utilities.domain.reqres.model.subsector.SubSector
import com.agree.ecosystem.utilities.presentation.menu.sectorsdialog.SectorsDialogFragment
import com.agree.ui.UiKitAttrs
import com.agree.ui.UiKitDimens
import com.agree.ui.snackbar.errorSnackBar
import com.devbase.presentation.recyclerview.endless.OnLoadMoreListener
import com.jakewharton.rxbinding3.widget.textChanges
import com.oratakashi.viewbinding.core.tools.visible
import com.telkom.legion.component.viewstate.ViewState
import com.telkom.legion.component.viewstate.showDefaultLayout
import com.telkom.legion.component.viewstate.showEmptyLayout
import com.telkom.legion.component.viewstate.showErrorLayout
import com.telkom.legion.component.viewstate.showLoadingLayout
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class VesselFragment :
    AgrFragment<FragmentVesselBinding>(),
    VesselDataContract,
    OnLoadMoreListener {

    private val subSectors: MutableList<SubSector> by lazy {
        ArrayList()
    }

    private var query = ""

    private var isRequestingData = false
    private var isLoadMoreFinish = false

    private val adapter: VesselAdapter by lazy {
        VesselAdapter { vessel ->
            vessel?.let {
                menuNav.fromVesselToDetailMonitoring(it.id)
            }
        }
    }

    override fun initUI() {
        super.initUI()
        with(binding) {
            rvVessel.adapter = adapter
            rvVessel.setItemSeparator()
            rvVessel.isNestedScrollingEnabled = false
            with(nestedScrollView) {
                viewTreeObserver.addOnScrollChangedListener {
                    val view = getChildAt(childCount - 1)
//                    if (view.bottom - (height + scrollY) == 0 && !isRequestingData && !isLoadMoreFinish) {
//                    if ((view.measuredHeight - measuredHeight) == scrollY && !isRequestingData && !isLoadMoreFinish) {
                    if (view.bottom - (height + scrollY) == 0 && !isRequestingData && !isLoadMoreFinish) {
                        fetchLoadMoreVessel()
                    }
                }
            }
        }
    }

    @SuppressLint("CheckResult")
    override fun initAction() {
        super.initAction()
        with(binding) {
            if (!connectivityObserver.isConnected) {
                cvFilter.setOnClickListener {
                    errorSnackBar(requireContext().resources.getString(R.string.label_error_filter_offline_mode))
                }
            }
            etSearch.editText?.let {
                it.textChanges()
                    .skipInitialValue()
                    .debounce(800, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { text ->
                        query = text.toString()
                        fetchListVessel()
                    }
            }
            msvVessel.getView(ViewState.ERROR)?.let {
                val emptyBinding = LayoutLostConnectionVesselBinding.bind(it)
                with(emptyBinding) {
                    btnReload.setOnClickListener {
                        fetchListVessel()
                    }
                }
            }
        }
    }

    private fun addChips() {
        with(binding) {
            cgVessel.addAll(subSectors.map { it.getFullSectorName() })
            cgVessel.setOnCloseIconClickListener { selectedSectors ->
                subSectors.removeAt(subSectors.indexOfLast { it.getFullSectorName() == selectedSectors })
                fetchListVessel()
            }
        }
    }

    private fun sectorDialog() {
        SectorsDialogFragment.setup {
            selectedSectors = subSectors.joinToString { it.getFullSectorName() }
            setCallbackListener { data ->
                subSectors.clear()
                subSectors.addAll(data)
                addChips()
                fetchListVessel()
            }
        }.show(childFragmentManager, "dialog")
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
        addObservers(VesselObserver(this, viewModel))
        connectivityObserver.observe().onEach {
            with(binding) {
                when (it) {
                    ConnectivityObserver.Status.Available -> {
                        cvFilter.setOnClickListener {
                            sectorDialog()
                        }
                        ivFilter.setOnClickListener {
                            with(cvFilter) {
                                performClick()
                                isPressed = true
                            }
                        }
                    }
                    else -> {
                        cvFilter.setOnClickListener {
                            errorSnackBar(requireContext().resources.getString(R.string.label_error_filter_offline_mode))
                        }
                    }
                }
            }
        }.launchIn(lifecycleScope)
        fetchListVessel()
    }

    override fun fetchListVessel() {
        if (!isRequestingData) {
            viewModel.getListVessel(
                userId = prefs.userId,
                subSectorId = subSectors.joinToString(separator = ",") { it.id },
                vesselName = query
            )
            isRequestingData = true
            isLoadMoreFinish = false
        }
        setFilterButtonColor(subSectors.isNotEmpty())
    }

    override fun fetchLoadMoreVessel() {
        isRequestingData = true
        viewModel.loadMoreVessel(
            userId = prefs.userId,
            subSectorId = subSectors.joinToString(separator = ",") { it.id },
            vesselName = query
        )
    }

    override fun onGetListVesselSuccess(data: List<Vessel>) {
        with(binding) {
            appBar.visible()
            msvVessel.showDefaultLayout()
            adapter.apply {
                setLoadMoreListener(this@VesselFragment)
                setEndlessScroll(rvVessel)
                resetEndlessScroll()
                clear()
                addOrUpdateAll(data)
            }
        }
        isRequestingData = false
    }

    override fun onGetListVesselLoading() {
        binding.msvVessel.showLoadingLayout()
    }

    override fun onGetListVesselEmpty() {
        binding.msvVessel.showEmptyLayout()
        binding.msvVessel.getView(ViewState.EMPTY)?.let {
            val emptyBinding = LayoutEmptyListVesselBinding.bind(it)
            with(emptyBinding) {
                btnRegister.setOnClickListener {
                    menuNav.fromListSubVesselToCompanies()
                }
            }
        }
        isRequestingData = false
    }

    override fun onGetListVesselFailed(e: Throwable?) {
        errorSnackBar(e?.message.toString())
        isRequestingData = false
        with(binding) {
            msvVessel.showErrorLayout()
        }
    }

    override fun onLoadMoreVesselSuccess(data: List<Vessel>) {
        if (adapter.itemCount > 0) {
            adapter.hideLoadMoreLoading()
        }
        adapter.addOrUpdateAll(data)
        isRequestingData = false
    }

    override fun onLoadMoreVesselLoading() {
        if (adapter.itemCount > 0) {
            adapter.showLoadMoreLoading()
        }
    }

    override fun onLoadMoreVesselEmpty() {
        adapter.finishLoadMore()
        isRequestingData = false
        isLoadMoreFinish = true
    }

    override fun onLoadMoreVesselFailed() {
        if (adapter.itemCount > 0) {
            adapter.showLoadMoreError()
        }
        isRequestingData = false
    }

    override fun onLoadMore(skip: Int?, limit: Int?, totalItemsCount: Int?, view: RecyclerView?) {
        // Do Nothing
    }

    override fun onLoadMoreRetryButtonClicked(skip: Int?, limit: Int?) {
        adapter.hideLoadMoreLoading()
        fetchLoadMoreVessel()
    }

    private val prefs: AgrPrefUsecase by inject()
    private val menuNav: MenuNavigation by navigation { activity }
    private val viewModel: VesselViewModel by viewModel()
    private val connectivityObserver: ConnectivityObserver by inject()
}
