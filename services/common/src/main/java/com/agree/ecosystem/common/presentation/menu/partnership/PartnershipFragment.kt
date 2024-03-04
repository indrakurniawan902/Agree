package com.agree.ecosystem.common.presentation.menu.partnership

import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.agree.ecosystem.common.databinding.FragmentPartnershipBinding
import com.agree.ecosystem.common.presentation.navigation.MenuNavigation
import com.agree.ecosystem.core.utils.base.AgrFragment
import com.agree.ecosystem.core.utils.domain.reqres.AgrPrefUsecase
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.core.utils.utility.extension.getAttrsValue
import com.agree.ecosystem.core.utils.utility.navigation.navigation
import com.agree.ecosystem.partnership.R
import com.agree.ecosystem.partnership.databinding.LayoutEmptyPartnershipBinding
import com.agree.ecosystem.partnership.domain.reqres.model.companymember.CompanyMember
import com.agree.ecosystem.utilities.domain.reqres.model.subsector.SubSector
import com.agree.ecosystem.utilities.presentation.menu.sectorsdialog.SectorsDialogFragment
import com.agree.ui.UiKitAttrs
import com.agree.ui.UiKitDimens
import com.agree.ui.snackbar.errorSnackBar
import com.devbase.presentation.recyclerview.endless.OnLoadMoreListener
import com.telkom.legion.component.viewstate.ViewState
import com.telkom.legion.component.viewstate.showDefaultLayout
import com.telkom.legion.component.viewstate.showEmptyLayout
import com.telkom.legion.component.viewstate.showLoadingLayout
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class PartnershipFragment :
    AgrFragment<FragmentPartnershipBinding>(),
    PartnershipDataContract,
    OnLoadMoreListener {

    private val selectedSector = mutableListOf<SubSector>()

    override fun initUI() {
        super.initUI()
        with(binding) {
            rvPartnership.adapter = adapter
            adapter.apply {
                setLoadMoreListener(this@PartnershipFragment)
                setEndlessScroll(rvPartnership)
                resetEndlessScroll()
            }
        }
    }

    override fun initData() {
        super.initData()
        applyDataChipGroup()
    }

    override fun initAction() {
        super.initAction()
        with(binding) {

            cvFilter.setOnClickListener {
//                SectorsDialogFragment(selectedSector.joinToString { it.getFullSectorName() }) {
//                    selectedSector.clear()
//                    selectedSector.addAll(it)
//                    applyDataChipGroup()
//                    cgSubSectors.setOnCloseIconClickListener { sector ->
//                        selectedSector.removeAt(selectedSector.indexOfLast { target -> target.getFullSectorName() == sector })
//                        cgSubSectors.isGone = selectedSector.isEmpty()
//                        fetchCompanyMembers()
//                    }
//                    fetchCompanyMembers()
//                }.show(childFragmentManager, "dialog")
                SectorsDialogFragment.setup {
                    selectedSectors = selectedSector.joinToString { it.getFullSectorName() }
                    setCallbackListener { data ->
                        selectedSector.clear()
                        selectedSector.addAll(data)
                        applyDataChipGroup()
                        cgSubSectors.setOnCloseIconClickListener { sector ->
                            selectedSector.removeAt(selectedSector.indexOfLast { target -> target.getFullSectorName() == sector })
                            cgSubSectors.isGone = selectedSector.isEmpty()
                            fetchCompanyMembers()
                        }
                        fetchCompanyMembers()
                    }
                }.show(childFragmentManager, "dialog")
            }

            btnStatusRegister.setOnClickListener {
                menuNav.fromPartnershipToStatusRegistration()
            }
            tvAddPartnership.setOnClickListener {
                menuNav.fromPartnershipToCompanies(selectedSector)
            }
        }
    }

    private fun applyDataChipGroup() {
        with(binding) {
            cgSubSectors.isVisible = selectedSector.isNotEmpty()
            if (selectedSector.isNotEmpty())
                cgSubSectors.addAll(selectedSector.map { subSector -> subSector.getFullSectorName() })
        }
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
        addObservers(PartnershipObserver(this, viewModel))
        fetchCompanyMembers()
    }

    override fun fetchCompanyMembers() {
        viewModel.getCompanyMembers(selectedSector.map { it.id }, prefs.userId)
        setFilterButtonColor(selectedSector.isNotEmpty())
    }

    override fun fetchLoadMoreCompanyMembers() {
        viewModel.loadMoreCompanyMembers(selectedSector.map { it.id }, prefs.userId)
    }

    override fun onLoadMore(skip: Int?, limit: Int?, totalItemsCount: Int?, view: RecyclerView?) {
        fetchLoadMoreCompanyMembers()
    }

    override fun onLoadMoreRetryButtonClicked(skip: Int?, limit: Int?) {
        adapter.hideLoadMoreLoading()
        fetchLoadMoreCompanyMembers()
    }

    override fun onGetCompanyMembersLoading() {
        binding.msvPartnership.showLoadingLayout()
    }

    override fun onGetCompanyMembersSuccess(data: List<CompanyMember>) {
        with(binding) {
            msvPartnership.showDefaultLayout()
            msvContent.showDefaultLayout()
            adapter.clear()
            adapter.addAll(data)
        }
    }

    override fun onGetCompanyMembersEmpty() {
        with(binding) {
            if (selectedSector.isEmpty()) {
                msvPartnership.showEmptyLayout()
                msvPartnership.getView(ViewState.EMPTY).let {
                    val emptyBinding = LayoutEmptyPartnershipBinding.bind(it)
                    with(emptyBinding) {
                        tvTitleEmptyPartnership.text =
                            getString(R.string.label_title_empty_partnership)
                        tvDescEmptyPartnership.text =
                            getString(R.string.label_description_empty_partnership)
                        btnRegister.setOnClickListener {
                            menuNav.fromPartnershipToCompanies(selectedSector)
                        }
                        btnCheckRegistrationStatus.setOnClickListener {
                            menuNav.fromPartnershipToStatusRegistration()
                        }
                    }
                }
            } else {
                msvPartnership.showDefaultLayout()
                msvContent.showEmptyLayout()
            }
        }
    }

    override fun onGetCompanyMembersFailed(e: Throwable?) {
        errorSnackBar(e?.message.toString())
    }

    override fun onLoadMoreSuccess(data: List<CompanyMember>) {
        if (adapter.itemCount > 0) {
            adapter.hideLoadMoreLoading()
        }
        adapter.addOrUpdateAll(data)
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
    }

    override fun onLoadMoreEmpty() {
        adapter.finishLoadMore()
    }

    private val menuNav: MenuNavigation by navigation { activity }
    private val viewModel: PartnershipViewModel by viewModel()
    private val prefs: AgrPrefUsecase by inject()
    private val adapter = CompanyMemberAdapter(onItemClicked = {
        menuNav.fromPartnershipToDetailPartnership(it.id)
    })
}
