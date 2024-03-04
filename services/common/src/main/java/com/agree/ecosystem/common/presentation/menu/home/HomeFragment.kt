package com.agree.ecosystem.common.presentation.menu.home

import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.agree.ecosystem.agreepedia.databinding.ItemAgreepediaArticleBinding
import com.agree.ecosystem.agreepedia.domain.model.article.Article
import com.agree.ecosystem.common.R
import com.agree.ecosystem.common.databinding.FragmentHomeBinding
import com.agree.ecosystem.common.databinding.ItemHomeMenuSectorsBinding
import com.agree.ecosystem.common.databinding.LayoutErrorHomeAgreepediaBinding
import com.agree.ecosystem.common.databinding.LayoutErrorHomePartnershipBinding
import com.agree.ecosystem.common.domain.reqres.model.home.companypartner.CompanyPartner
import com.agree.ecosystem.common.domain.reqres.model.home.loan.MyLoan
import com.agree.ecosystem.common.domain.reqres.model.home.loan.MyLoanActive
import com.agree.ecosystem.common.presentation.menu.monitoring.subvessels.SubSectorViewModel
import com.agree.ecosystem.common.presentation.navigation.MenuNavigation
import com.agree.ecosystem.core.utils.base.AgrFragment
import com.agree.ecosystem.core.utils.domain.reqres.AgrPrefUsecase
import com.agree.ecosystem.core.utils.utility.ConverterDate
import com.agree.ecosystem.core.utils.utility.defaultErrorHandling
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.core.utils.utility.navigation.navigation
import com.agree.ecosystem.core.utils.utility.offline.ConnectivityObserver
import com.agree.ecosystem.users.domain.reqres.model.profile.Profile
import com.agree.ecosystem.utilities.domain.reqres.model.subsector.SubSector
import com.devbase.presentation.recyclerview.setupWith
import com.devbase.utils.ext.gone
import com.devbase.utils.ext.toDate
import com.devbase.utils.ext.toString
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.oratakashi.viewbinding.core.tools.visible
import com.telkom.legion.component.utility.extension.setLegionDivider
import com.telkom.legion.component.viewstate.ViewState
import com.telkom.legion.component.viewstate.showDefaultLayout
import com.telkom.legion.component.viewstate.showEmptyLayout
import com.telkom.legion.component.viewstate.showErrorLayout
import com.telkom.legion.component.viewstate.showLoadingLayout
import com.telkom.legion.extension.snackbar.LgnSnackBar
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : AgrFragment<FragmentHomeBinding>(), HomeDataContract {
    private val adapter: HomeAdapter by lazy {
        HomeAdapter { company ->
            company?.let {
                menuNav.fromHomeToCompanyPartnerDetail(company.id)
            }
        }
    }

    private val loanActiveAdapter: LoanActiveHomeAdapter by lazy {
        LoanActiveHomeAdapter { myLoan ->
            myLoan?.let {
                if (it.loanId == "") {
                    menuNav.fromHomeToFinance(2)
                } else {
                    menuNav.fromHomeToDetaiActiveLoan()
                }
            }
        }
    }

    private val connectivityObserver: ConnectivityObserver by inject()

    override fun initUI() {
        super.initUI()
        with(binding) {
            rvCompanyPartner.adapter = adapter
            rvListLoanActive.adapter = loanActiveAdapter
            btnFaq.setOnClickListener {
                menuNav.fromHomeToHelp()
            }
            btnHomeBanner.setOnClickListener {
                menuNav.fromHomeToCompanies()
            }
            tvSeeAllArticle.setOnClickListener {
                menuNav.fromHomeToAgreepedia()
            }
            tvSeeAllPartner.setOnClickListener {
                menuNav.fromHomeToCompanies()
            }
        }

        if (connectivityObserver.isConnected) {
            getProfile()
            getSubSectors()
            getCompanyPartner()
            getAgreepediaArticles()
            getLoanActive(prefs.userId)
        } else {
            with(binding) {
                msvAgreePedia.showErrorLayout()
                tvSeeAllArticle.gone()
                with(LayoutErrorHomeAgreepediaBinding.bind(msvAgreePedia.getView(ViewState.ERROR))) {
                    btnReload.setOnClickListener {
                        getAgreepediaArticles()
                    }
                }

                msvCompanyPartner.showErrorLayout()
                tvSeeAllPartner.gone()
                with(LayoutErrorHomePartnershipBinding.bind(msvCompanyPartner.getView(ViewState.ERROR))) {
                    btnReload.setOnClickListener {
                        getCompanyPartner()
                    }
                }
            }
        }
    }

    override fun initObserver() {
        super.initObserver()
        addObservers(HomeObserver(this, viewModel))

//        connectivityObserver.observe().onEach {
//            if (it != ConnectivityObserver.Status.Available) {
//                BottomSheetConnectionFragment().show(
//                    childFragmentManager,
//                    "bottomsheet_connection"
//                )
//            }
//        }.launchIn(lifecycleScope)
    }

    override fun onGetProfileLoading() {
        binding.msvProfile.showLoadingLayout()
    }

    override fun onGetProfileSuccess(data: Profile) {
        with(binding) {
            msvProfile.showDefaultLayout()
            ivPhoto.url = data.avatar
            tvName.text = data.name
            prefs.name = data.name
        }
    }

    override fun onGetSubSectorsLoading() {
        binding.msvSubSectors.showLoadingLayout()
    }

    override fun onGetSubSectorsSuccess(data: List<SubSector>) {
        with(binding) {
            msvSubSectors.showDefaultLayout()
            rvMenuSectors.setupWith<SubSector> {
                withLayoutManager(
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                )
                withBinding<ItemHomeMenuSectorsBinding> { item, binding ->
                    with(binding) {
                        item?.let {
                            tvName.text = it.getFullSectorName()
                            if (it.icon.isNotEmpty()) {
//                                ivIcon.load(it.icon)
                                ivIcon.url = it.icon
                            } else {
                                ivIcon.resId = R.drawable.ic_subsector_error
//                                ivIcon.load(R.drawable.ic_subsector_error)
                            }
                        }
                    }
                }
                withClick {
                    it?.let { sectorMenu ->
                        sharedVm.setSubSector(sectorMenu)
                        menuNav.fromHomeSectorToListSubVessel()
                    }
                }
            }.apply { addAll(data) }
        }
    }

    override fun onGetSubSectorsFailed(e: Throwable?) {
        e?.printStackTrace()
    }

    override fun onGetCompanyPartnerLoading() {
        binding.msvCompanyPartner.showLoadingLayout()
    }

    override fun onGetCompanyPartnerSuccess(data: List<CompanyPartner>) {
        with(binding) {
            tvSeeAllPartner.visible()
            msvCompanyPartner.showDefaultLayout()
        }
        adapter.clear()
        adapter.addOrUpdateAll(data)
    }

    override fun onGetAgreepediaArticlesLoading() {
        binding.msvAgreePedia.showLoadingLayout()
    }

    override fun onGetAgreepediaArticlesSuccess(data: List<Article>) {
        with(binding) {
            msvAgreePedia.showDefaultLayout()
            tvSeeAllArticle.visible()
            rvAgreePedia.setupWith<Article> {
                withLayoutManager(
                    FlexboxLayoutManager(requireContext()).apply {
                        flexDirection = FlexDirection.COLUMN
                    }
                )
                withBinding<ItemAgreepediaArticleBinding> { item, binding ->
                    with(binding) {
                        item?.let {
                            tvTitleArticle.text = it.blogName
                            tvAuthorArticle.text = it.createdBy
                            ivImgArticle.url = it.blogImage
                            tvDateArticle.text =
                                it.beginDate.toDate(ConverterDate.SQL_DATE.formatter)
                                    ?.toString(ConverterDate.SIMPLE_DATE.formatter).orEmpty()
                        }
                    }
                }
                withClick { agreepedia ->
                    agreepedia?.let { menuNav.fromHomeToAgreepediaDetail(it) }
                }
                rvAgreePedia.setLegionDivider()
            }.apply {
                addAll(data)
            }
        }
    }

    override fun onGetAgreepediaArticlesFailed(e: Throwable?) {
        with(binding) {
            msvAgreePedia.showErrorLayout()
            tvSeeAllArticle.gone()
            with(LayoutErrorHomeAgreepediaBinding.bind(msvAgreePedia.getView(ViewState.ERROR))) {
                btnReload.setOnClickListener {
                    getAgreepediaArticles()
                }
            }
        }
    }

    override fun onGetCompanyPartnerFailed(e: Throwable?) {
        with(binding) {
            msvCompanyPartner.showErrorLayout()
            tvSeeAllPartner.gone()
            with(LayoutErrorHomePartnershipBinding.bind(msvCompanyPartner.getView(ViewState.ERROR))) {
                btnReload.setOnClickListener {
                    getCompanyPartner()
                }
            }
        }
    }

    override fun onGetAgreepediaArticlesEmpty() {
        binding.msvAgreePedia.showEmptyLayout()
    }

    override fun getLoanActive(idUser: String) {
        viewModel.getListLoanActive(idUser)
    }

    override fun onGetLoanActiveLoading() {
        binding.msvLoanActive.showLoadingLayout()
    }

    override fun onGetLoanActiveSuccess(data: List<MyLoanActive>) {
        with(binding) {
            val lastLoanNull = MyLoan("", "", 0, 0, 0, listOf(), "", "", "")
            val newDataLoans = data.get(0).loans.toMutableList()
            newDataLoans.add(lastLoanNull)

            msvLoanActive.showDefaultLayout()
            tvMoreLoanPackage.visible()
            tvMoreLoanPackage.setOnClickListener { menuNav.fromHomeToFinance(0) }
            loanActiveAdapter.apply {
                setEndlessScroll(rvListLoanActive)
                addOrUpdateAll(newDataLoans)
            }
        }
    }

    override fun onGetLoanActiveEmpty() {
        with(binding) {
            msvLoanActive.showEmptyLayout()
            msvLoanActive.getView(ViewState.EMPTY).setOnClickListener {
                menuNav.fromHomeToFinance(0)
            }
        }
    }

    override fun onGetLoanActiveFailed(e: Throwable?) {
        /*
        val datas = LoanActiveFakeDatas().listLoanActive
        onGetLoanActiveSuccess(datas)
         */
        onGetLoanActiveEmpty()
    }

    override fun initAction() {
        super.initAction()
        getProfile()
        getSubSectors()
        getCompanyPartner()
        getAgreepediaArticles()
        getModalActiveStatus()
    }

    override fun getProfile() {
        viewModel.getProfile(prefs.userId) {
            defaultErrorHandling(this, it.orEmpty()) { getProfile() }
        }
    }

    override fun getSubSectors() {
        with(binding) {
            viewModel.getSubSectors {
                msvSubSectors.gone()

                LgnSnackBar.setup(requireActivity()) {
                    title = getString(R.string.error_load_subsector)
                    actionText = getString(R.string.label_try_again)
                    duration = Snackbar.LENGTH_SHORT
                    type = LgnSnackBar.ToastType.ERROR
                    view = binding.root
                    setActionButtonListener {
                        msvSubSectors.visible()
                        getSubSectors()
                    }
                }.show()
            }
        }
    }

    override fun getCompanyPartner() {
        viewModel.getCompanyPartner()
    }

    override fun getAgreepediaArticles() {
        viewModel.getAgreepediaArticles()
    }

    override fun getModalActiveStatus() {
        viewModel.getModalActive()
    }

    override fun onGetModalActiveSuccess(data: Boolean) {
        binding.containerFinance.isVisible = data

        if (!data) {
            return
        }

        getLoanActive(prefs.userId)
    }

    private val viewModel: HomeViewModel by viewModel()
    private val sharedVm: SubSectorViewModel by activityViewModels()
    private val menuNav: MenuNavigation by navigation { activity }
    private val prefs: AgrPrefUsecase by inject()
}
