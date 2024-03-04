package com.agree.ecosystem.partnership.presentation.menu.companies

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.devbase.data.source.VmData

class CompaniesObserver(
    private val view: CompaniesDataContract,
    private val viewModel: CompaniesViewModel
) : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        viewModel.companies.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onGetCompaniesLoading()
                is VmData.Success -> {
                    view.onGetCompaniesSuccess(it.data)
                    viewModel.page++
                }
                is VmData.Failure -> view.onGetCompaniesFailed(it.throwable)
                is VmData.Empty -> view.onGetCompaniesEmpty()
            }
        }
        viewModel.loadMoreCompanies.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onLoadMoreLoading()
                is VmData.Success -> {
                    viewModel.page++
                    view.onLoadMoreSuccess(it.data)
                }
                is VmData.Failure -> view.onLoadMoreFailed()
                is VmData.Empty -> view.onLoadMoreEmpty()
            }
        }
    }
}
