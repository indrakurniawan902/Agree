package com.agree.ecosystem.common.presentation.menu.partnership

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.devbase.data.source.VmData

class PartnershipObserver(
    private val view: PartnershipDataContract,
    private val viewModel: PartnershipViewModel,
) : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        viewModel.companyMembers.observe(owner) {

            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onGetCompanyMembersLoading()
                is VmData.Success -> {
                    view.onGetCompanyMembersSuccess(it.data)
                    viewModel.page++
                }
                is VmData.Failure -> view.onGetCompanyMembersFailed(it.throwable)
                is VmData.Empty -> view.onGetCompanyMembersEmpty()
            }
        }
        viewModel.loadMoreCompanyMembers.observe(owner) {
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
