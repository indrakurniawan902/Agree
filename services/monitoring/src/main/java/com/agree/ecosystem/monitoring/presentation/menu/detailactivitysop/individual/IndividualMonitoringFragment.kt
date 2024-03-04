package com.agree.ecosystem.monitoring.presentation.menu.detailactivitysop.individual

import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.agree.ecosystem.core.utils.base.AgrFragment
import com.agree.ecosystem.core.utils.domain.reqres.AgrPrefUsecase
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.core.utils.utility.navigation.navigation
import com.agree.ecosystem.monitoring.databinding.FragmentIndividualMonitoringBinding
import com.agree.ecosystem.monitoring.databinding.ItemEntryPointBinding
import com.agree.ecosystem.monitoring.databinding.LayoutErrorEntryPointBinding
import com.agree.ecosystem.monitoring.databinding.LayoutErrorListIndividualMonitoringBinding
import com.agree.ecosystem.monitoring.domain.reqres.model.detailactivitysop.SopActivityDetail
import com.agree.ecosystem.monitoring.domain.reqres.model.subvesselIndividual.IndividualSubVessel
import com.agree.ecosystem.monitoring.presentation.menu.detailactivitysop.guidedialog.GuideDialogFragment
import com.agree.ecosystem.monitoring.presentation.navigation.DetailActivitySopNavigation
import com.agree.ecosystem.monitoring.utils.MonitoringType
import com.devbase.presentation.recyclerview.endless.OnLoadMoreListener
import com.telkom.legion.component.utility.extension.setLegionDivider
import com.telkom.legion.component.viewstate.ViewState
import com.telkom.legion.component.viewstate.showDefaultLayout
import com.telkom.legion.component.viewstate.showEmptyLayout
import com.telkom.legion.component.viewstate.showErrorLayout
import com.telkom.legion.component.viewstate.showLoadingLayout
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class IndividualMonitoringFragment :
    AgrFragment<FragmentIndividualMonitoringBinding>(),
    IndividualMonitoringDataContract,
    OnLoadMoreListener {

    override fun initData() {
        super.initData()
        with(binding) {
            toolbar.text = args.activityName
            toolbar.setBackButtonOnClick {
                requireActivity().onBackPressed()
            }
            btnEdit.visibility =
                if (args.type == MonitoringType.ENTRY_POINT.value) View.VISIBLE
                else View.GONE
        }
    }

    override fun initObserver() {
        super.initObserver()
        addObservers(IndividualMonitoringObserver(this, viewModel))
    }

    override fun onResume() {
        super.onResume()
        getView()?.setFocusableInTouchMode(true)
        getView()?.requestFocus()
        getView()?.setOnKeyListener(
            View.OnKeyListener { v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    getView()?.requestFocus()
                    requireActivity().onBackPressed()
                    finishActivity()
                    return@OnKeyListener true
                }
                false
            }
        )
    }
    override fun initUI() {
        super.initUI()
        with(binding) {
            toolbar.setBackButtonOnClick {
                requireActivity().onBackPressed()
                finishActivity()
            }
            rvIndividualMonitoring.apply {
                val mAdapter = this@IndividualMonitoringFragment.adapter
                adapter = mAdapter
                setLegionDivider {
                    dividerInsetStart = 50
                }
            }
            tvShowGuide.setOnClickListener {
                GuideDialogFragment(args.activityName, args.guideContent, args.date).showNow(
                    childFragmentManager, "dialog"
                )
            }
            with(LayoutErrorListIndividualMonitoringBinding.bind(msvContent.getView(ViewState.ERROR))) {
                btnRetry.setOnClickListener { //
                }
            }

            btnEdit.setOnClickListener {
                detailActivitySopNav.fromIndividualMonitoringToDetailActivitySop()
            }
        }
    }

    override fun initAction() {
        super.initAction()
        fetchIndividualMonitoring()
    }

    override fun fetchIndividualMonitoring() {
        val subVesselId = args.subVesselId
        viewModel.getEntryPoint(subVesselId)
        viewModel.getIndividualMonitoring(subVesselId, prefs.userId)
    }

    override fun fetchLoadMoreIndividualMonitoring() {
        viewModel.loadMoreIndividualMonitoring(args.subVesselId, prefs.userId)
    }

    override fun onEntryPointLoading() {
        binding.msvEntryPoint.showLoadingLayout()
    }

    override fun onEntryPointSuccess(data: SopActivityDetail) {
        val formSchema = data.formSchema
        formSchema.formSchemaItem.forEach {
            val value = formSchema.activeformSchema.find { active -> active.id == it.id }
            val suffix = if (it.suffix.isNotEmpty()) " ".plus(it.suffix) else String()
            val item = ItemEntryPointBinding.inflate(LayoutInflater.from(context))
            item.tvLabel.text = it.label
            item.tvValue.text = value?.value.plus(suffix)
            binding.containerEntryPoint.addView(item.root)
        }
        binding.msvEntryPoint.showDefaultLayout()
    }

    override fun onEntryPointError(e: Throwable?) {
        with(binding) {
            msvEntryPoint.showErrorLayout()

            val errorView = msvEntryPoint.getView(ViewState.ERROR)

            with(LayoutErrorEntryPointBinding.bind(errorView)) {
                btnRetry.setOnClickListener {
                    viewModel.getEntryPoint(args.subVesselId)
                }
            }
        }
    }

    override fun onIndividualLoading() {
        binding.msvContent.showLoadingLayout()
    }

    override fun onIndividualSuccess(data: List<IndividualSubVessel>) {
        with(binding) {
            msvContent.showDefaultLayout()
            adapter.apply {
                setLoadMoreListener(this@IndividualMonitoringFragment)
                setEndlessScroll(rvIndividualMonitoring)
                resetEndlessScroll()
                clear()
                addOrUpdateAll(data)
            }
        }
    }

    override fun onIndividualEmpty() {
        binding.msvContent.showEmptyLayout()
    }

    override fun onIndividualError(e: Throwable?) {
        with(binding) {
            msvContent.showErrorLayout()
            val errorView = msvContent.getView(ViewState.ERROR)

            with(LayoutErrorListIndividualMonitoringBinding.bind(errorView)) {
                btnRetry.setOnClickListener {
                    viewModel.getIndividualMonitoring(args.subVesselId, prefs.userId)
                }
            }
        }
    }

    override fun onLoadMoreIndividualLoading() {
        if (adapter.itemCount > 0) {
            adapter.showLoadMoreLoading()
        }
    }

    override fun onLoadMoreIndividualSuccess(data: List<IndividualSubVessel>) {
        if (adapter.itemCount > 0) {
            adapter.hideLoadMoreLoading()
        }
        adapter.addOrUpdateAll(data)
    }

    override fun onLoadMoreIndividualEmpty() {
        adapter.finishLoadMore()
    }

    override fun onLoadMoreIndividualError(e: Throwable?) {
        if (adapter.itemCount > 0) {
            adapter.showLoadMoreError()
        }
    }

    override fun onLoadMore(skip: Int?, limit: Int?, totalItemsCount: Int?, view: RecyclerView?) {
        fetchLoadMoreIndividualMonitoring()
    }

    override fun onLoadMoreRetryButtonClicked(skip: Int?, limit: Int?) {
        adapter.hideLoadMoreLoading()
        fetchLoadMoreIndividualMonitoring()
    }

    private val viewModel: IndividualMonitoringViewModel by viewModel()
    private val detailActivitySopNav: DetailActivitySopNavigation by navigation { activity }
    private val args: IndividualMonitoringFragmentArgs by navArgs()
    private val prefs: AgrPrefUsecase by inject()
    private val adapter: IndividualMonitoringAdapter by lazy {
        IndividualMonitoringAdapter {
            if (args.type != MonitoringType.ENTRY_POINT.value) {
                detailActivitySopNav.fromIndividualMonitoringToDetailActivitySop(it)
            }
        }
    }
}
