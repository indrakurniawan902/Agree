package com.agree.ecosystem.common.presentation.menu.monitoring.subvessels

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.agree.ecosystem.common.R
import com.agree.ecosystem.common.databinding.FragmentSubVesselBinding
import com.agree.ecosystem.common.databinding.LayoutEmptyListSubVesselBinding
import com.agree.ecosystem.common.databinding.LayoutLostConnectionVesselBinding
import com.agree.ecosystem.common.presentation.navigation.MenuNavigation
import com.agree.ecosystem.core.utils.base.AgrFragment
import com.agree.ecosystem.core.utils.domain.reqres.AgrPrefUsecase
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.core.utils.utility.extension.getAttrsValue
import com.agree.ecosystem.core.utils.utility.navigation.navigation
import com.agree.ecosystem.monitoring.domain.reqres.model.subvessel.SubVessel
import com.agree.ecosystem.utilities.domain.reqres.model.subsector.SubSector
import com.agree.ecosystem.utilities.presentation.menu.sectorsdialog.SectorsDialogFragment
import com.agree.ui.UiKitAttrs
import com.agree.ui.UiKitDimens
import com.agree.ui.snackbar.errorSnackBar
import com.agree.ui.widget.coachmark.CoachMark
import com.agree.ui.widget.coachmark.CoachMarkDialog
import com.devbase.presentation.recyclerview.endless.OnLoadMoreListener
import com.jakewharton.rxbinding3.widget.textChanges
import com.oratakashi.viewbinding.core.tools.visible
import com.telkom.legion.component.utility.enums.ButtonColors
import com.telkom.legion.component.utility.extension.requiredColor
import com.telkom.legion.component.viewstate.ViewState
import com.telkom.legion.component.viewstate.showDefaultLayout
import com.telkom.legion.component.viewstate.showEmptyLayout
import com.telkom.legion.component.viewstate.showErrorLayout
import com.telkom.legion.component.viewstate.showLoadingLayout
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit
import com.agree.ecosystem.core.utils.R as coreRes

class SubVesselFragment :
    AgrFragment<FragmentSubVesselBinding>(),
    SubVesselDataContract,
    OnLoadMoreListener {

    private val subSectors: MutableList<SubSector> by lazy {
        ArrayList()
    }

    /**
     * [query] is used to determine the search query
     */
    private var query = ""

    /**
     * [hasSmartFarming] is used to determine whether the request is for smart farming or not
     */
    private var hasSmartFarming = false

    private var isRequestingData = false
    private var isLoadMoreFinish = false

    override fun initUI() {
        super.initUI()
        with(binding) {
            rvSubVessel.adapter = adapter

            if (subSectors.size > 0) addChips()
            rvSubVessel.isNestedScrollingEnabled = false
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
                sectorDialog()
            }
            ivFilter.setOnClickListener {
                with(cvFilter) {
                    performClick()
                    isPressed = true
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
                        fetchListSubVessel()
                    }
            }
            msvSubVessel.getView(ViewState.ERROR)?.let {
                val emptyBinding = LayoutLostConnectionVesselBinding.bind(it)
                with(emptyBinding) {
                    btnReload.setOnClickListener {
                        fetchListSubVessel()
                    }
                }
            }
        }
    }

    private fun sectorDialog() {
        SectorsDialogFragment.setup {
            selectedSectors = subSectors.joinToString { it.getFullSectorName() }
            isSmartFarming = true
            hasSmartFarming = this@SubVesselFragment.hasSmartFarming

            setCallbackListener { data, isChecked ->
                subSectors.clear()
                sharedVm.validateAndRemove(data)
                subSectors.addAll(data)
                this@SubVesselFragment.hasSmartFarming = isChecked
                addChips()
            }
        }.show(childFragmentManager, "dialog")
    }

    private fun addChips() {
        with(binding) {
            cgSubVessel.addAll(
                subSectors.map { it.getFullSectorName() }
                    .toMutableList()
                    .apply {
                        if (!hasSmartFarming) return@apply

                        add(0, getString(coreRes.string.label_smartfarming))
                    }
            )
            cgSubVessel.setOnCloseIconClickListener { chipName ->
                sharedVm.validateAndRemove(chipName)
                if (chipName == getString(coreRes.string.label_smartfarming)) {
                    hasSmartFarming = false
                } else {
                    subSectors.removeAt(subSectors.indexOfLast { it.getFullSectorName() == chipName })
                }
                fetchListSubVessel()
            }
            fetchListSubVessel()
        }
    }

    private fun setFilterButtonColor(isFilter: Boolean) {
        if (isAdded) {
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
    }

    override fun initObserver() {
        super.initObserver()
        addObservers(SubVesselObserver(this, viewModel))
        if (sharedVm.selectedSector.value != null) {
            sharedVm.selectedSector.value?.let { subSector ->
                subSectors.add(subSector)
                addChips()
            }
        } else {
            fetchListSubVessel()
        }
    }

    override fun fetchListSubVessel() {
        if (!isRequestingData) {
            viewModel.getListSubVessel(
                userId = prefs.userId,
                search = query,
                subSectorId = subSectors.joinToString(separator = ",") { it.id },
                hasSmartFarming
            )
            isRequestingData = true
            isLoadMoreFinish = false
        }
        setFilterButtonColor(subSectors.isNotEmpty() || hasSmartFarming)
    }

    override fun fetchLoadMoreSubVessel() {
        isRequestingData = true
        viewModel.loadMoreSubVessel(
            userId = prefs.userId,
            search = query,
            subSectorId = subSectors.joinToString(separator = ",") { it.id },
            hasSmartFarming
        )
    }

    override fun onResume() {
        super.onResume()
        fetchListSubVessel()
    }

    override fun onGetListSubVesselSuccess(data: List<SubVessel>) {
        with(binding) {
            msvSubVessel.showDefaultLayout()
            appBar.visible()
            adapter.apply {
                setLoadMoreListener(this@SubVesselFragment)
                setEndlessScroll(rvSubVessel)
                resetEndlessScroll()
                adapter.clear()
                adapter.addOrUpdateAll(data)
            }
        }
        isRequestingData = false
        if (data.isNotEmpty() && prefs.shouldShowSmartFarmingCoachmark) {
            showSmartFarmingCoachmark()
        }
    }

    override fun onGetListSubVesselLoading() {
        binding.msvSubVessel.showLoadingLayout()
    }

    override fun onGetListSubVesselEmpty() {
        with(binding) {
            msvSubVessel.showEmptyLayout()
            msvSubVessel.getView(ViewState.EMPTY).let {
                val emptyBinding = LayoutEmptyListSubVesselBinding.bind(it)
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
        with(binding) {
            msvSubVessel.showErrorLayout()
        }
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

    override fun onLoadMoreSubVesselFailed() {
        if (adapter.itemCount > 0) {
            adapter.showLoadMoreError()
        }
        isRequestingData = false
        with(binding) {
            msvSubVessel.showErrorLayout()
        }
    }

    override fun onLoadMoreSubVesselEmpty() {
        adapter.finishLoadMore()
        isRequestingData = false
        isLoadMoreFinish = true
    }

    override fun onLoadMore(skip: Int?, limit: Int?, totalItemsCount: Int?, view: RecyclerView?) {
//        fetchLoadMoreSubVessel()
    }

    override fun onLoadMoreRetryButtonClicked(skip: Int?, limit: Int?) {
        adapter.hideLoadMoreLoading()
        fetchLoadMoreSubVessel()
    }

    override fun showSmartFarmingCoachmark() {
        with(binding) {
            runCatching {
                adapter.addToFirst(viewModel.generateDummySmartFarmingData())
                lifecycleScope.launch {
                    delay(500)
                    CoachMark.setup(requireActivity()) {
                        buttonNextText = getString(R.string.action_continue)
                        buttonFinishText = getString(R.string.action_done)
                        textTitleColor =
                            requireContext().requiredColor(UiKitAttrs.info_500)
                        buttonNextColor = ButtonColors.INFO

                        addCoachMark {
                            view = cvFilter
                            title = getString(R.string.smartfarming_label)
                            description =
                                getString(R.string.label_smartfarming_first_coachmark_description)
                        }

                        addCoachMark {
                            view =
                                rvSubVessel.findViewHolderForLayoutPosition(0)?.itemView?.findViewById<ConstraintLayout>(
                                    R.id.clSmartFarmingLabel
                                )
                            isBackground = true
                            title = getString(R.string.smartfarming_label)
                            description =
                                getString(R.string.label_smartfarming_second_coachmark_description)
                        }
                    }.apply {
                        addOnStepListener(object : CoachMarkDialog.OnStepListener {
                            override fun onStep(
                                previous: Int,
                                current: Int,
                                coachMark: CoachMark?
                            ): Boolean = false

                            override fun onComplete() {
                                adapter.removeAt(0)
                                prefs.shouldShowSmartFarmingCoachmark = false
                            }
                        })
                        show()
                    }
                }
            }
        }
    }

    private val prefs: AgrPrefUsecase by inject()
    private val menuNav: MenuNavigation by navigation { activity }
    private val viewModel: SubVesselViewModel by viewModel()
    private val sharedVm: SubSectorViewModel by activityViewModels()
    private val adapter: SubVesselAdapter by lazy {
        SubVesselAdapter { subVessel ->
            subVessel?.let {
                menuNav.fromHomeToDetailSubVessel(
                    it.id,
                    it.sectorId
                )
            }
        }
    }
}
