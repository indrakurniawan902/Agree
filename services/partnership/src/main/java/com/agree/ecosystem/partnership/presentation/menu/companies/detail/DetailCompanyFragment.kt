package com.agree.ecosystem.partnership.presentation.menu.companies.detail

import androidx.navigation.fragment.navArgs
import com.agree.ecosystem.core.utils.base.AgrFragment
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.core.utils.utility.extension.collapse
import com.agree.ecosystem.core.utils.utility.extension.expand
import com.agree.ecosystem.core.utils.utility.navigation.navigation
import com.agree.ecosystem.partnership.R
import com.agree.ecosystem.partnership.databinding.FragmentDetailCompanyBinding
import com.agree.ecosystem.partnership.domain.reqres.model.company.Company
import com.agree.ecosystem.partnership.domain.reqres.model.validation.Validation
import com.agree.ecosystem.partnership.presentation.menu.registration.RegistrationBottomSheet
import com.agree.ecosystem.partnership.presentation.navigation.RegistrationNavigation
import com.agree.ecosystem.utilities.domain.reqres.model.commodity.Commodity
import com.devbase.utils.ext.gone
import com.devbase.utils.ext.isNotNull
import com.devbase.utils.ext.visible
import com.telkom.legion.component.viewstate.showDefaultLayout
import com.telkom.legion.component.viewstate.showErrorLayout
import com.telkom.legion.component.viewstate.showLoadingLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailCompanyFragment :
    AgrFragment<FragmentDetailCompanyBinding>(),
    DetailCompanyDataContract {
    private var isExpanded = false
    lateinit var detailCompany: Company
    private val detailCompanyCommodityAdapter = DetailCompanyCommodityAdapter()
    private var registered = false
    private val args: DetailCompanyFragmentArgs by navArgs()
    lateinit var dataCommodity: List<Commodity>

    override fun initUI() {
        super.initUI()
        with(binding) {
            rvCommodities.adapter = detailCompanyCommodityAdapter
        }
    }

    override fun initAction() {
        super.initAction()
        with(binding) {
            toolbar.setBackButtonOnClick { requireActivity().onBackPressed() }
            btnRegisterPartnership.setOnClickListener {
                if (detailCompany.isNotNull()) {
                    if (!registered) {
                        navigation.fromCompanyDetailsToRegistration(detailCompany, dataCommodity)
                    } else {
                        RegistrationBottomSheet("Registered").showNow(
                            requireActivity().supportFragmentManager,
                            "dialogRegistered"
                        )
                    }
                }
            }
            tvReadMore.setOnClickListener {
                isExpanded = if (isExpanded) {
                    tvDescription.collapse()
                    tvReadMore.text = getString(R.string.label_see_more)
                    false
                } else {
                    tvDescription.expand()
                    tvReadMore.text = getString(R.string.label_show_less)
                    true
                }
            }
        }
    }

    override fun initObserver() {
        super.initObserver()
        addObservers(DetailCompanyObserver(this, viewModel))
        args.companyId?.let {
            viewModel.getDetailCompany(it)
        }
    }

    override fun onGetDetailCompanyLoading() {
        with(binding) {
            msvDetailCompany.showLoadingLayout()
            btnRegisterPartnership.isEnable = false
        }
    }

    override fun onGetDetailCompanySuccess(data: Company) {
        with(binding) {
            detailCompany = data
            msvDetailCompany.showDefaultLayout()
            btnRegisterPartnership.isEnable = true
            imgCompanyLogo.url = data.image
            tvCompanyName.text = data.name
            tvSubSectors.text = data.subSectors.joinToString { it.getSubSectorLabel() }
            tvDescription.text = data.about
            tvCompanyAddress.text = data.address
            tvPartnershipArea.text = if (data.isArea) {
                data.scopeAreas.joinToString { it.provinceName }
            } else {
                root.context.getString(R.string.label_all_indonesia)
            }
            val labelAll = context?.getString(R.string.label_all) ?: ""
            cgSubSectors.apply {
                val commodities = mutableListOf<String>().apply {
                    add(labelAll)
                    addAll(data.subSectors.map { it.getSubSectorLabel() })
                }
                addAll(commodities)
                text = labelAll

                setListener { label ->
                    val subSector = data.subSectors.singleOrNull { it.getSubSectorLabel() == label }
                    viewModel.getSubSectorList(data.id, subSector?.subSectorId ?: "")
                }
            }
            tvDescription.post {
                if (tvDescription.lineCount > 4) {
                    tvDescription.collapse()
                    tvReadMore.visible()
                } else {
                    tvReadMore.gone()
                }
            }
        }
    }

    override fun onGetSubSectorLoading() {
        binding.msvCommodities.showLoadingLayout()
        binding.btnRegisterPartnership.isEnable = false
    }

    override fun onGetSubSectorSuccess(data: List<Commodity>) {
        with(binding) {
            btnRegisterPartnership.isEnable = true
            if (cgSubSectors.text.trim() == "Semua") dataCommodity = data
            msvCommodities.showDefaultLayout()
            detailCompanyCommodityAdapter.clear()
            detailCompanyCommodityAdapter.addOrUpdateAll(data)
        }
    }

    override fun onGetSubSectorFailed(e: Throwable?) {
        super.onGetSubSectorFailed(e)
        detailCompanyCommodityAdapter.clear()
        binding.msvCommodities.showErrorLayout()
    }

    override fun onValidationLoading() {
        // Do Nothing
    }

    override fun onValidationSuccess(data: Validation) {
        registered = data.registered.toBooleanStrict()
    }

    override fun onValidationEmpty() {
        // Do Nothing
    }

    private val viewModel: DetailCompanyViewModel by viewModel()
    private val navigation: RegistrationNavigation by navigation { activity }
}
