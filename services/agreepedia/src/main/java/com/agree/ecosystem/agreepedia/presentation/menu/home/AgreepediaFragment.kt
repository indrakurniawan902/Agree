package com.agree.ecosystem.agreepedia.presentation.menu.home

import android.graphics.PorterDuff
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import com.agree.ecosystem.agreepedia.databinding.FragmentAgreepediaBinding
import com.agree.ecosystem.agreepedia.domain.model.article.Article
import com.agree.ecosystem.agreepedia.presentation.menu.dialog.SectorDialogFragment
import com.agree.ecosystem.agreepedia.presentation.navigation.MainNavigation
import com.agree.ecosystem.agreepedia.utility.enums.FilterSector
import com.agree.ecosystem.agreepedia.utility.enums.FilterSort
import com.agree.ecosystem.core.utils.base.AgrFragment
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.core.utils.utility.extension.setItemSeparator
import com.agree.ecosystem.core.utils.utility.navigation.navigation
import com.agree.ui.UiKitAttrs
import com.agree.ui.UiKitDimens
import com.devbase.presentation.recyclerview.endless.OnLoadMoreListener
import com.oratakashi.viewbinding.core.tools.gone
import com.oratakashi.viewbinding.core.tools.visible
import com.telkom.legion.component.utility.extension.resolveColor
import com.telkom.legion.component.viewstate.showDefaultLayout
import com.telkom.legion.component.viewstate.showEmptyLayout
import com.telkom.legion.component.viewstate.showLoadingLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

class AgreepediaFragment :
    AgrFragment<FragmentAgreepediaBinding>(),
    AgreepediaDataContract,
    OnLoadMoreListener {

    private var query = ""
    private val subSector: MutableList<String> by lazy {
        ArrayList()
    }

    override fun initUI() {
        super.initUI()
        with(binding) {
            rvArticles.adapter = adapter
            rvArticles.setItemSeparator()
            nestedScrollView.setOnScrollChangeListener(
                NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, oldScrollY ->
                    if (v.getChildAt(v.childCount - 1) != null && scrollY >= v.getChildAt(v.childCount - 1).measuredHeight - v.measuredHeight &&
                        scrollY > oldScrollY
                    ) {
                        fetchLoadMoreAgreepediaArticles()
                    }
                }
            )
        }
        addChips()
    }

    override fun initAction() {
        super.initAction()
        with(binding) {
            toolbar.setBackButtonOnClick { requireActivity().onBackPressed() }
            etSearch.doOnTextChanged { text, _, _, _ ->
                query = text
                showTextResult(query)
                fetchAgreepediaArticles()
            }

            cvFilter.setOnClickListener {
                sectorDialog()
            }
            ivFilter.setOnClickListener {
                with(cvFilter) {
                    performClick()
                    isPressed = true
                }
            }
        }
    }

    private fun showTextResult(text: String) {
        with(binding) {
            if (text != "") {
                tvshowResult.visible()
                tvResultKeyword.visible()
            } else {
                tvshowResult.gone()
                tvResultKeyword.gone()
            }
            tvResultKeyword.text = "\"$text\""
        }
    }

    private fun sectorDialog() {
        SectorDialogFragment(
            viewModel
        ) {
            subSector.clear()
            if (viewModel.filterSector.value.value != "") {
                subSector.add(viewModel.filterSector.value.value)
            }
            addChips()
            fetchAgreepediaArticles()
        }.show(childFragmentManager, "dialogAgreepedia")
    }

    private fun addChips() {
        with(binding) {
            cgFilters.visible()
            cgFilters.addAll(subSector.map { it })
            cgFilters.setOnCloseIconClickListener {
                subSector.removeFirst()
                viewModel.filterSort.value = FilterSort.LATEST
                viewModel.filterSector.value = FilterSector.SHOWALL
                cgFilters.gone()
                fetchAgreepediaArticles()
            }
        }
    }

    override fun initObserver() {
        super.initObserver()
        addObservers(AgreepediaObserver(this, viewModel))
        fetchAgreepediaArticles()
    }

    override fun fetchAgreepediaArticles() {
        viewModel.getAgreepediaArticles(search = query)
    }

    override fun onFilterStateChanged(sector: FilterSector) {
        with(binding) {
            if (sector != FilterSector.SHOWALL) {
                cvFilter.strokeWidth = resources.getDimension(UiKitDimens.dimen_2dp).toInt()
                requireContext().resolveColor(UiKitAttrs.colorPrimary)?.let {
                    ivFilter.setColorFilter(
                        it,
                        PorterDuff.Mode.SRC_IN
                    )
                    cvFilter.strokeColor = it
                }
            } else {
                cvFilter.strokeWidth = resources.getDimension(UiKitDimens.dimen_1dp).toInt()
                requireContext().resolveColor(UiKitAttrs.black_900)?.let {
                    ivFilter.setColorFilter(
                        it,
                        PorterDuff.Mode.SRC_IN
                    )
                }
                requireContext().resolveColor(UiKitAttrs.black_600)?.let {
                    cvFilter.strokeColor = it
                }
            }
        }
    }

    override fun fetchLoadMoreAgreepediaArticles() {
        viewModel.loadMoreAgreepediaArticles(search = query)
    }

    override fun onGetAgreepediaArticlesLoading() {
        binding.msvArticles.showLoadingLayout()
    }

    override fun onGetAgreepediaArticlesSuccess(data: List<Article>) {
        with(binding) {
            msvArticles.showDefaultLayout()
            adapter.apply {
                setLoadMoreListener(this@AgreepediaFragment)
                setEndlessScroll(rvArticles)
                resetEndlessScroll()
                clear()
                addOrUpdateAll(data)
            }
        }
    }

    override fun onGetAgreepediaArticlesEmpty() {
        binding.msvArticles.showEmptyLayout()
    }

    override fun onLoadMoreSuccess(data: List<Article>) {
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
        // fetchLoadMoreAgreepediaArticles()
    }

    override fun onLoadMoreRetryButtonClicked(skip: Int?, limit: Int?) {
        adapter.hideLoadMoreLoading()
        fetchLoadMoreAgreepediaArticles()
    }

    private val viewModel: AgreepediaViewModel by viewModel()
    private val menuNav: MainNavigation by navigation { activity }
    private val adapter: AgreepediaAdapter by lazy {
        AgreepediaAdapter(onItemClicked = { agreepedia ->
            menuNav.fromHomeToAgreepediaDetail(agreepedia)
        })
    }
}
