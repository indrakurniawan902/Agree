package com.agree.ecosystem.partnership.presentation.menu.companies

import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.agree.ecosystem.core.utils.base.AgrFragment
import com.agree.ecosystem.core.utils.domain.reqres.AgrPrefUsecase
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.core.utils.utility.extension.getAttrsValue
import com.agree.ecosystem.core.utils.utility.navigation.navigation
import com.agree.ecosystem.partnership.databinding.FragmentCompaniesBinding
import com.agree.ecosystem.partnership.domain.reqres.model.company.Company
import com.agree.ecosystem.partnership.presentation.navigation.RegistrationNavigation
import com.agree.ecosystem.utilities.domain.reqres.model.subsector.SubSector
import com.agree.ecosystem.utilities.presentation.menu.sectorsdialog.SectorsDialogFragment
import com.agree.ui.UiKitAttrs
import com.agree.ui.UiKitDimens
import com.devbase.presentation.recyclerview.endless.OnLoadMoreListener
import com.jakewharton.rxbinding3.widget.textChanges
import com.telkom.legion.component.viewstate.showDefaultLayout
import com.telkom.legion.component.viewstate.showEmptyLayout
import com.telkom.legion.component.viewstate.showLoadingLayout
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class CompaniesFragment :
    AgrFragment<FragmentCompaniesBinding>(),
    CompaniesDataContract,
    OnLoadMoreListener {

    private var args: Array<SubSector>? = arrayOf()

    private val subSectors: MutableList<SubSector> by lazy {
        ArrayList()
    }

    private var query = ""

    private var isRequestingLoadMore = false
    private var isLoadMoreFinished = false

    override fun initData() {
        super.initData()
        args = requireActivity().intent?.extras?.getBundle("selectedSector")?.let {
            CompaniesFragmentArgs.fromBundle(it).selectedSector
        }
    }

    override fun initUI() {
        super.initUI()
        with(binding) {
            toolbar.setBackButtonOnClick { requireActivity().onBackPressed() }
            rvCompanies.adapter = adapter
            if (args != null) {
                args?.apply {
                    subSectors.addAll(this)
                    cgCompanies.apply {
                        isVisible = subSectors.isNotEmpty()
                        addAll(subSectors.map { it.getFullSectorName() })
                    }
                }
            }
            if (subSectors.size > 0) addChips()
            rvCompanies.isNestedScrollingEnabled = false
            with(nestedScrollView) {
                viewTreeObserver.addOnScrollChangedListener {
                    val view = getChildAt(childCount - 1)
                    if (view.bottom - (height + scrollY) == 0 && !isRequestingLoadMore && !isLoadMoreFinished) {
                        fetchLoadMoreListCompany()
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
                        fetchListCompany()
                    }
            }
        }
    }

    private fun addChips() {
        with(binding) {
            cgCompanies.apply {
                isVisible = subSectors.isNotEmpty()
                addAll(subSectors.map { it.getFullSectorName() })
                setOnCloseIconClickListener { selectedSectors ->
                    subSectors.removeAt(subSectors.indexOfLast { it.getFullSectorName() == selectedSectors })
                    isGone = subSectors.isEmpty()
                    fetchListCompany()
                }
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
                fetchListCompany()
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
        addObservers(CompaniesObserver(this, viewModel))
        fetchListCompany()
    }

    override fun fetchListCompany() {
        viewModel.getCompanies(
            subSectorId = subSectors.joinToString(separator = ",") { it.id },
            userId = prefs.userId,
            search = query
        )
        isLoadMoreFinished = false
        setFilterButtonColor(subSectors.isNotEmpty())
    }

    override fun fetchLoadMoreListCompany() {
        isRequestingLoadMore = true
        viewModel.loadMoreCompanies(
            subSectorId = subSectors.joinToString(separator = ",") { it.id },
            userId = prefs.userId,
            search = query
        )
    }

    override fun onGetCompaniesLoading() {
        binding.msvCompanies.showLoadingLayout()
    }

    override fun onGetCompaniesSuccess(data: List<Company>) {
        with(binding) {
            msvCompanies.showDefaultLayout()
            adapter.apply {
                setLoadMoreListener(this@CompaniesFragment)
                setEndlessScroll(rvCompanies)
                resetEndlessScroll()
                clear()
                addOrUpdateAll(data)
            }
        }
    }

    override fun onGetCompaniesEmpty() {
        binding.msvCompanies.showEmptyLayout()
    }

    override fun onLoadMoreSuccess(data: List<Company>) {
        if (adapter.itemCount > 0) {
            adapter.hideLoadMoreLoading()
        }
        adapter.addOrUpdateAll(data)
        isRequestingLoadMore = false
    }

    override fun onLoadMoreLoading() {
        if (adapter.itemCount > 0) {
            adapter.showLoadMoreLoading()
        }
    }

    override fun onLoadMoreFailed() {
        if (adapter.itemCount > 0) {
            adapter.showLoadMoreError()
        }
        isRequestingLoadMore = false
    }

    override fun onLoadMoreEmpty() {
        adapter.finishLoadMore()
        isRequestingLoadMore = false
        isLoadMoreFinished = true
    }

    override fun onLoadMore(skip: Int?, limit: Int?, totalItemsCount: Int?, view: RecyclerView?) {
        // Load More Request Move to setOnScrollChangeListener cause use NestedScrollView
    }

    override fun onLoadMoreRetryButtonClicked(skip: Int?, limit: Int?) {
        adapter.hideLoadMoreLoading()
        fetchLoadMoreListCompany()
    }

    private val viewModel: CompaniesViewModel by viewModel()
    private val adapter: CompaniesAdapter by lazy {
        CompaniesAdapter(onItemClicked = {
            registerNav.fromCompanyListToCompanyDetail(it.id)
        })
    }

    private val prefs: AgrPrefUsecase by inject()
    private val registerNav: RegistrationNavigation by navigation { activity }
}
