package com.agree.ecosystem.finances.presentation.menu.finance.cultivator.data.listcultivator

import androidx.recyclerview.widget.RecyclerView
import com.agree.ecosystem.core.utils.base.AgrFragment
import com.agree.ecosystem.core.utils.domain.reqres.AgrPrefUsecase
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.core.utils.utility.navigation.navigation
import com.agree.ecosystem.finances.databinding.FragmentListCultivatorBinding
import com.agree.ecosystem.finances.domain.reqres.model.CultivatorBorrower
import com.agree.ecosystem.finances.domain.reqres.model.params.ProfileCultivatorParams
import com.agree.ecosystem.finances.presentation.navigation.ProfileCultivatorNavigation
import com.devbase.presentation.recyclerview.endless.OnLoadMoreListener
import com.oratakashi.viewbinding.core.tools.finish
import com.telkom.legion.component.utility.extension.setLegionDivider
import com.telkom.legion.component.viewstate.showDefaultLayout
import com.telkom.legion.component.viewstate.showEmptyLayout
import com.telkom.legion.component.viewstate.showLoadingLayout
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListCultivatorFragment : AgrFragment<FragmentListCultivatorBinding>(),
    ListCultivatorDataContract, OnLoadMoreListener {

    override fun initUI() {
        super.initUI()
        with(binding) {
            rvListCultivator.adapter = adapter
            rvListCultivator.setLegionDivider()
        }
    }

    override fun initAction() {
        super.initAction()
        with(binding) {
            toolbar.setBackButtonOnClick { finish() }
        }
    }

    override fun initObserver() {
        super.initObserver()
        addObservers(ListCultivatorObserver(this, viewModel))
        fetchListCultivator(prefs.userId)
    }

    override fun fetchListCultivator(mitraId: String) {
        viewModel.fetchListCultivatorPartnership(mitraId)
    }

    override fun listCultivatorOnLoading() {
        binding.msvListCultivator.showLoadingLayout()
    }

    override fun listCultivatorOnSuccess(data: List<CultivatorBorrower>) {
        /*
        with(binding) {
            if (data.isEmpty()) {
                listCultivatorOnEmpty(data)
            } else {
                msvListCultivator.showDefaultLayout()
                rvListCultivator.setupWith<CultivatorBorrower> {
                    withLayoutManager(
                        LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                    )
                    withBinding<ItemCultivatorMemberBinding> { item, binding ->
                        with(binding) {
                            item?.let {
                                tvCultivatorName.text = it.name
                                tvValueNik.text = it.nik
                                ivCultivatorPhoto.url = it.image
                            }
                        }
                    }
                    withClick { dataCultivator ->
                        dataCultivator?.let {
                            dataCultivatorNav.fromListCultivatorToProfileCultivator()
                        }
                    }
                    rvListCultivator.setLegionDivider()
                }.apply {
                    addAll(data)
                }
            }
        }
         */
        with(binding) {
            msvListCultivator.showDefaultLayout()
            adapter.apply {
                addOrUpdateAll(data)
            }

        }
    }

    override fun listCultivatorOnEmpty(data: List<CultivatorBorrower>) {
        binding.msvListCultivator.showEmptyLayout()
    }

    override fun listCultivatorOnError(e: Throwable?) {
        listCultivatorOnEmpty(listOf())
    }

    override fun fetchLoadMoreListCultivator(userId: String) {
        viewModel.fetchLoadMoreListCultivatorPartnership(userId)
    }

    override fun loadMoreListCultivatorOnLoading() {
        if (adapter.itemCount > 0) {
            adapter.showLoadMoreLoading()
        }
    }

    override fun loadMoreListCultivatorOnSuccess(data: List<CultivatorBorrower>) {
        if (adapter.itemCount > 0) {
            adapter.hideLoadMoreLoading()
        } else {
            loadMoreListCultivatorOnEmpty(data)
        }
        adapter.addOrUpdateAll(data)
    }

    override fun loadMoreListCultivatorOnEmpty(data: List<CultivatorBorrower>) {
        adapter.finishLoadMore()
    }

    override fun loadMoreListCultivatorOnError(e: Throwable?) {
        if (adapter.itemCount > 0) {
            adapter.showLoadMoreError()
        }
    }

    override fun onLoadMore(skip: Int?, limit: Int?, totalItemsCount: Int?, view: RecyclerView?) {
        fetchLoadMoreListCultivator(prefs.userId)
    }

    override fun onLoadMoreRetryButtonClicked(skip: Int?, limit: Int?) {
        adapter.hideLoadMoreLoading()
        fetchLoadMoreListCultivator(prefs.userId)
    }

    private val dataCultivatorNav: ProfileCultivatorNavigation by navigation { activity }
    private val viewModel: ListCultivatorViewModel by viewModel()
    private val prefs: AgrPrefUsecase by inject()
    private val adapter: ListCultivatorAdapter by lazy {
        ListCultivatorAdapter {
            it.let {
                if (it != null) {
                    dataCultivatorNav.fromListCultivatorToProfileCultivator(
                        ProfileCultivatorParams(
                            cultivator = it,
                            detailLoanPackage = null,
                            isFromLoan = false
                        )
                    )
                }
            }
        }
    }
}
