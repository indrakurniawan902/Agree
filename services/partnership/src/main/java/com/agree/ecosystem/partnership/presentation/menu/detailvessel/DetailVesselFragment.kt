package com.agree.ecosystem.partnership.presentation.menu.detailvessel

import androidx.recyclerview.widget.RecyclerView
import com.agree.ecosystem.core.utils.base.AgrFragment
import com.agree.ecosystem.core.utils.utility.DialogUtils
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.core.utils.utility.navigation.navigation
import com.agree.ecosystem.monitoring.domain.reqres.model.subvessel.SubVessel
import com.agree.ecosystem.partnership.R
import com.agree.ecosystem.partnership.databinding.FragmentDetailVesselBinding
import com.agree.ecosystem.partnership.domain.reqres.model.vessel.Vessel
import com.agree.ecosystem.partnership.presentation.navigation.DetailVesselNavigationUsecase
import com.agree.ui.snackbar.successSnackBar
import com.devbase.presentation.recyclerview.endless.OnLoadMoreListener
import com.telkom.legion.component.viewstate.showDefaultLayout
import com.telkom.legion.component.viewstate.showEmptyLayout
import com.telkom.legion.component.viewstate.showLoadingLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailVesselFragment :
    AgrFragment<FragmentDetailVesselBinding>(),
    DetailVesselDataContract,
    OnLoadMoreListener {

    private var vessel: Vessel? = null

    override fun initData() {
        super.initData()
        vessel = activity?.intent?.getParcelableExtra("vessel")
    }

    override fun initUI() {
        super.initUI()
        with(binding) {
            vessel?.let {
                toolbar.text = it.name
                tvVesselId.text = getString(R.string.label_id_area_value, it.code)
                tvVesselName.text = it.name
                tvLocation.text = it.provinceName
                tvVesselArea.text =
                    getString(R.string.label_farm_actual_size_value, it.realizationSize, it.size)
                tvSubVesselArea.text = getString(R.string.label_farm_size_value, it.realizationSize)
            }
            rvSubVessels.adapter = adapter
            adapter.apply {
                setLoadMoreListener(this@DetailVesselFragment)
                setEndlessScroll(rvSubVessels)
                resetEndlessScroll()
            }
        }
    }

    override fun initAction() {
        super.initAction()
        with(binding) {
            toolbar.setBackButtonOnClick { requireActivity().onBackPressed() }
            ivEdit.setOnClickListener {
                DialogUtils.showCustomFormDialog(
                    context = requireContext(),
                    title = getString(R.string.label_change_vessel_name_dialog_title),
                    message = getString(R.string.label_change_vessel_name_dialog_subtitle),
                    text = tvVesselName.text.toString(),
                    placeholder = getString(R.string.label_change_vessel_name_dialog_placeholder),
                    positiveAction = Pair(getString(R.string.action_yes)) {
                        tvVesselName.text = it
                        successSnackBar(getString(R.string.label_success_update_vessel_name))
                    },
                    negativeAction = Pair(getString(R.string.action_cancel), null)
                )
            }
        }
    }

    override fun initObserver() {
        super.initObserver()
        addObservers(DetailVesselObserver(this, viewModel))
        viewModel.getListSubVessel(vessel?.id.orEmpty())
    }

    override fun onGetListSubVesselLoading() {
        binding.msvListSubVessel.showLoadingLayout()
    }

    override fun onGetListSubVesselSuccess(data: List<SubVessel>) {
        binding.msvListSubVessel.showDefaultLayout()
        adapter.clear()
        adapter.addOrUpdateAll(data)
    }

    override fun onGetListSubVesselEmpty() {
        binding.msvListSubVessel.showEmptyLayout()
    }

    override fun onLoadMoreSuccess(data: List<SubVessel>) {
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

    override fun onLoadMore(skip: Int?, limit: Int?, totalItemsCount: Int?, view: RecyclerView?) {
        viewModel.loadMoreListSubVessel(vessel?.id.orEmpty())
    }

    override fun onLoadMoreRetryButtonClicked(skip: Int?, limit: Int?) {
        adapter.hideLoadMoreLoading()
        viewModel.loadMoreListSubVessel(vessel?.id.orEmpty())
    }

    private val viewModel: DetailVesselViewModel by viewModel()
    private val adapter = DetailVesselAdapter(onItemClick = {
        detailVesselNav.fromDetailVesselToDetailSubVessel(it)
    })
    private val detailVesselNav: DetailVesselNavigationUsecase by navigation { activity }
}
