package com.agree.ecosystem.common.presentation.menu.partnership.detail

import androidx.navigation.fragment.navArgs
import com.agree.ecosystem.common.R
import com.agree.ecosystem.common.databinding.FragmentDetailPartnershipBinding
import com.agree.ecosystem.common.presentation.navigation.MenuNavigation
import com.agree.ecosystem.core.utils.base.AgrFragment
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.core.utils.utility.extension.getAttrsValue
import com.agree.ecosystem.core.utils.utility.extension.getSectorColor
import com.agree.ecosystem.core.utils.utility.navigation.navigation
import com.agree.ecosystem.partnership.databinding.ItemDetailPartnershipBinding
import com.agree.ecosystem.partnership.domain.reqres.model.companymember.CompanyMemberDetail
import com.agree.ui.UiKitAttrs
import com.agree.ui.snackbar.errorSnackBar
import com.devbase.presentation.recyclerview.setupWith
import com.devbase.utils.util.getColorResource
import com.devbase.utils.util.getStringResource
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.telkom.legion.component.viewstate.showDefaultLayout
import com.telkom.legion.component.viewstate.showLoadingLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailPartnershipFragment :
    AgrFragment<FragmentDetailPartnershipBinding>(),
    DetailPartnershipDataContract {

    private val args: DetailPartnershipFragmentArgs by navArgs()

    override fun initAction() {
        super.initAction()
        with(binding) {
            toolbar.navController = menuNav.getNavController()
            fetchCompanyMemberDetails()
        }
    }

    override fun initUI() {
        super.initUI()
    }

    override fun initObserver() {
        super.initObserver()
        addObservers(DetailPartnershipObserver(this, viewModel))
    }

    override fun fetchCompanyMemberDetails() {
        viewModel.getCompanyMemberDetails(args.companyId)
    }

    override fun onGetCompanyMemberDetailsLoading() {
        binding.msvDetailPartnership.showLoadingLayout()
    }

    override fun onGetCompanyMemberDetailsSuccess(data: CompanyMemberDetail) {
        with(binding) {
            msvDetailPartnership.showDefaultLayout()
            tvCompanyName.text = data.companyName
            imgCompanyLogo.url = data.companyImage
            tvViewCompanyDetails.setOnClickListener {
                menuNav.fromDetailPartnershipToDetailCompany(data.companyId)
            }
            rvSectors.setupWith<CompanyMemberDetail.CompanyCommodity> {
                withLayoutManager(
                    FlexboxLayoutManager(requireContext()).apply {
                        flexDirection = FlexDirection.COLUMN
                    }
                )
                withBinding<ItemDetailPartnershipBinding> { item, binding ->
                    with(binding) {
                        item?.let {
                            tvSubSectorName.text = it.subSectorName
                            tvSectorName.text = it.sectorName
                            tvCommodities.apply {
                                if (it.data.isEmpty()) {
                                    text =
                                        getStringResource(R.string.label_no_partnered_commodities_yet)
                                    setTextColor(UiKitAttrs.error_normal.getAttrsValue(context))
                                } else {
                                    text = it.data.joinToString { commodity -> commodity.name }
                                    setTextColor(UiKitAttrs.info_normal.getAttrsValue(context))
                                }
                            }
                            viewSectorColor.setBackgroundColor(
                                getColorResource(
                                    it.sectorName.lowercase().getSectorColor()
                                )
                            )
                        }
                    }
                }
            }.apply {
                addAll(data.commodities)
            }
        }
    }

    override fun onGetCompanyMemberDetailsFailed(e: Throwable?) {
        errorSnackBar(e?.message.toString())
    }

    private val menuNav: MenuNavigation by navigation { activity }
    private val viewModel: DetailPartnershipViewModel by viewModel()
}
