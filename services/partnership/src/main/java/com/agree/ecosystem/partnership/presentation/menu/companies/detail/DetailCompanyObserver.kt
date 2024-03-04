package com.agree.ecosystem.partnership.presentation.menu.companies.detail

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.agree.ecosystem.core.utils.utility.exception.EmptyException
import com.devbase.data.source.VmData

class DetailCompanyObserver(
    private val view: DetailCompanyDataContract,
    private val viewModel: DetailCompanyViewModel
) : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        viewModel.company.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onGetDetailCompanyLoading()
                is VmData.Success -> view.onGetDetailCompanySuccess(it.data)
                is VmData.Failure -> view.onGetDetailCompanyFailed(it.throwable)
                is VmData.Empty -> view.onGetDetailCompanyFailed(EmptyException())
            }
        }
        viewModel.subSector.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onGetSubSectorLoading()
                is VmData.Success -> view.onGetSubSectorSuccess(it.data)
                is VmData.Failure -> view.onGetSubSectorFailed(it.throwable)
                is VmData.Empty -> view.onGetSubSectorFailed(EmptyException())
            }
        }
        viewModel.validation.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onValidationLoading()
                is VmData.Success -> {
                    view.onValidationSuccess(it.data)
                }
                is VmData.Failure -> view.onValidationFailed(it.throwable)
                is VmData.Empty -> view.onValidationEmpty()
            }
        }
    }
}
