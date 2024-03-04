package com.agree.ecosystem.partnership.presentation.menu.statusregistration

import androidx.recyclerview.widget.RecyclerView
import com.agree.ecosystem.core.utils.base.AgrFragment
import com.agree.ecosystem.core.utils.domain.reqres.AgrPrefUsecase
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.core.utils.utility.navigation.navigation
import com.agree.ecosystem.partnership.R
import com.agree.ecosystem.partnership.data.reqres.model.registrationstatus.RegistrationStatusQuery
import com.agree.ecosystem.partnership.databinding.FragmentStatusRegistrationBinding
import com.agree.ecosystem.partnership.databinding.LayoutEmptyRegistrationStatusBinding
import com.agree.ecosystem.partnership.domain.reqres.model.registrationstatus.RegistrationStatus
import com.agree.ecosystem.partnership.presentation.navigation.ListStatusRegisterNavigation
import com.devbase.presentation.recyclerview.endless.OnLoadMoreListener
import com.devbase.utils.util.getStringResource
import com.telkom.legion.component.viewstate.ViewState
import com.telkom.legion.component.viewstate.showDefaultLayout
import com.telkom.legion.component.viewstate.showEmptyLayout
import com.telkom.legion.component.viewstate.showLoadingLayout
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class StatusRegistrationFragment :
    AgrFragment<FragmentStatusRegistrationBinding>(),
    StatusRegistrationDataContract,
    OnLoadMoreListener {

    private val adapter: StatusRegistrationAdapter by lazy {
        StatusRegistrationAdapter(onItemClicked = {
            listStatusNav.fromStatusRegistrationToDetailStatus(it.id)
        })
    }

    override fun initAction() {
        super.initAction()
        with(binding) {
            toolbar.setBackButtonOnClick { requireActivity().onBackPressed() }
        }
    }

    override fun onResume() {
        super.onResume()
        fetchRegistrationStatusList()
    }

    override fun initUI() {
        super.initUI()
        with(binding) {
            rvRegistration.adapter = adapter
        }
    }

    override fun initObserver() {
        super.initObserver()
        addObservers(StatusRegistrationObserver(this, viewModel))
    }

    override fun fetchRegistrationStatusList() {
        viewModel.getRegistrationStatusList(
            query = RegistrationStatusQuery(prefs.userId)
        )
    }

    override fun onGetRegistrationStatusListLoading() {
        binding.msvRegistrationStatus.showLoadingLayout()
    }

    override fun onGetRegistrationStatusListSuccess(data: List<RegistrationStatus>) {
        with(binding) {
            msvRegistrationStatus.showDefaultLayout()
            adapter.apply {
                setLoadMoreListener(this@StatusRegistrationFragment)
                setEndlessScroll(rvRegistration)
                resetEndlessScroll()
                adapter.clear()
                adapter.addOrUpdateAll(data)
            }
        }
    }

    override fun onRegistrationStatusListEmpty() {
        with(binding) {
            msvRegistrationStatus.showEmptyLayout()
            msvRegistrationStatus.getView(ViewState.EMPTY)?.let {
                val emptyBinding = LayoutEmptyRegistrationStatusBinding.bind(it)
                with(emptyBinding) {
                    tvTitleEmptyPartnership.text =
                        getStringResource(R.string.label_title_empty_registration_status)
                    tvDescEmptyPartnership.text =
                        getStringResource(R.string.label_description_empty_registration_status)
                    btnRegister.setOnClickListener {
                        listStatusNav.fromHomeToCompanies()
                    }
                }
            }
        }
    }

    override fun onLoadMoreLoading() {
        if (adapter.itemCount > 0) {
            adapter.showLoadMoreLoading()
        }
    }

    override fun onLoadMoreSuccess(data: List<RegistrationStatus>) {
        if (adapter.itemCount > 0) {
            adapter.hideLoadMoreLoading()
        }
        adapter.addOrUpdateAll(data)
    }

    override fun onLoadMoreFailed() {
        if (adapter.itemCount > 0) {
            adapter.showLoadMoreError()
        }
    }

    override fun onLoadMoreEmpty() {
        adapter.finishLoadMore()
    }

    override fun onLoadMore(skip: Int?, limit: Int?, totalItemsCount: Int?, view: RecyclerView?) {
        viewModel.loadMoreRegistrationStatusList(RegistrationStatusQuery(prefs.userId))
    }

    override fun onLoadMoreRetryButtonClicked(skip: Int?, limit: Int?) {
        adapter.hideLoadMoreLoading()
        viewModel.loadMoreRegistrationStatusList(RegistrationStatusQuery(prefs.userId))
    }

    private val viewModel: StatusRegistrationViewModel by viewModel()
    private val prefs: AgrPrefUsecase by inject()
    private val listStatusNav: ListStatusRegisterNavigation by navigation { activity }
}
