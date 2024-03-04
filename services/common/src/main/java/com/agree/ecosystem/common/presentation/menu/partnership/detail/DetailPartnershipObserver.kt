package com.agree.ecosystem.common.presentation.menu.partnership.detail

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.agree.ecosystem.core.utils.utility.exception.EmptyException
import com.devbase.data.source.VmData

class DetailPartnershipObserver(
    private val view: DetailPartnershipDataContract,
    private val viewModel: DetailPartnershipViewModel
) : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        viewModel.companyMemberDetails.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onGetCompanyMemberDetailsLoading()
                is VmData.Success -> view.onGetCompanyMemberDetailsSuccess(it.data)
                is VmData.Failure -> view.onGetCompanyMemberDetailsFailed(it.throwable)
                is VmData.Empty -> view.onGetCompanyMemberDetailsFailed(EmptyException())
            }
        }
    }
}
