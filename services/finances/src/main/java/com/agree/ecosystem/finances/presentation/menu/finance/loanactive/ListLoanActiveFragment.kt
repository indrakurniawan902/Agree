package com.agree.ecosystem.finances.presentation.menu.finance.loanactive

import androidx.recyclerview.widget.RecyclerView
import com.agree.ecosystem.core.utils.base.AgrFragment
import com.agree.ecosystem.core.utils.utility.extension.addObservers
import com.agree.ecosystem.finances.databinding.FragmentLoanActiveBinding
import com.agree.ecosystem.finances.databinding.LayoutEmptyListListLoanActiveBinding
import com.agree.ecosystem.finances.domain.reqres.model.MyLoanData
import com.devbase.presentation.recyclerview.endless.OnLoadMoreListener
import com.telkom.legion.component.viewstate.ViewState
import com.telkom.legion.component.viewstate.showDefaultLayout
import com.telkom.legion.component.viewstate.showEmptyLayout
import com.telkom.legion.component.viewstate.showLoadingLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListLoanActiveFragment : AgrFragment<FragmentLoanActiveBinding>(),
    ListLoanActiveDataContract, OnLoadMoreListener {

    override fun initUI() {
        super.initUI()
        with(binding) {
            rvLoanPackage.adapter = adapter
            adapter.apply {
                setLoadMoreListener(this@ListLoanActiveFragment)
                setEndlessScroll(rvLoanPackage)
                resetEndlessScroll()
            }
        }
    }

    override fun initObserver() {
        super.initObserver()
        addObservers(ListLoanActiveObserver(this, viewModel))
        fetchListLoanActive("")
    }

    override fun fetchListLoanActive(userId: String) {
        viewModel.fetchListLoanActive(userId)
    }

    override fun listLoanActiveOnLoading() {
        with(binding) {
            msvLoanActive.showLoadingLayout()
        }
    }

    override fun listLoanActiveOnSuccess(data: List<MyLoanData>) {
        with(binding) {
            if (data.isEmpty()) {
                listLoanActiveOnEmpty(listOf())
            } else {
                msvLoanActive.showDefaultLayout()
            }
        }
    }

    override fun listLoanActiveOnEmpty(data: List<MyLoanData>) {
        with(binding) {
            msvLoanActive.showEmptyLayout()
        }
    }

    override fun listLoanActiveOnError(e: Throwable?) {
//        errorSnackBar(e?.message ?: "")
        listLoanActiveOnEmpty(listOf())
    }

    override fun fetchLoadMoreListLoanActive(userId: String) {
        viewModel.fetchLoadMoreListLoanActive(userId)
    }

    override fun loadMoreListLoanActiveOnLoading() {
        if (adapter.itemCount > 0) {
            adapter.showLoadMoreLoading()
        }
    }

    override fun loadMoreListLoanActiveOnSuccess(data: List<MyLoanData>) {
        if (adapter.itemCount > 0) {
            adapter.showLoadMoreLoading()
        } else {
            loadMoreListLoanActiveOnEmpty(data)
        }
        adapter.addOrUpdateAll(data)
    }

    override fun loadMoreListLoanActiveOnEmpty(data: List<MyLoanData>) {
        adapter.finishLoadMore()
    }

    override fun loadMoreListLoanActiveOnError(e: Throwable?) {
        if (adapter.itemCount > 0) {
            adapter.showLoadMoreError()
        }
    }

    override fun onLoadMore(skip: Int?, limit: Int?, totalItemsCount: Int?, view: RecyclerView?) {
        fetchLoadMoreListLoanActive("")
    }

    override fun onLoadMoreRetryButtonClicked(skip: Int?, limit: Int?) {
        adapter.hideLoadMoreLoading()
        fetchLoadMoreListLoanActive("")
    }

    private val viewModel: ListLoanActiveViewModel by viewModel()
    private val adapter = ListLoanActiveAdapter({

    })
}
