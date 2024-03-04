package com.agree.ecosystem.common.presentation.menu.home

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.agree.ecosystem.core.utils.utility.exception.EmptyException
import com.devbase.data.source.VmData

class HomeObserver(
    private val view: HomeDataContract,
    private val viewModel: HomeViewModel
) : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        viewModel.profile.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onGetProfileLoading()
                is VmData.Success -> view.onGetProfileSuccess(it.data)
                is VmData.Failure -> view.onGetProfileFailed(it.throwable)
                is VmData.Empty -> view.onGetProfileFailed(EmptyException())
            }
        }
        viewModel.subSectors.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onGetSubSectorsLoading()
                is VmData.Success -> view.onGetSubSectorsSuccess(it.data)
                is VmData.Failure -> view.onGetSubSectorsFailed(it.throwable)
                is VmData.Empty -> view.onGetSubSectorsFailed(EmptyException())
            }
        }

        viewModel.companyPartner.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onGetCompanyPartnerLoading()
                is VmData.Success -> view.onGetCompanyPartnerSuccess(it.data)
                is VmData.Failure -> view.onGetCompanyPartnerFailed(it.throwable)
                is VmData.Empty -> view.onGetCompanyPartnerFailed(EmptyException())
            }
        }

        viewModel.articles.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onGetAgreepediaArticlesLoading()
                is VmData.Success -> view.onGetAgreepediaArticlesSuccess(it.data)
                is VmData.Failure -> view.onGetAgreepediaArticlesFailed(it.throwable)
                is VmData.Empty -> view.onGetAgreepediaArticlesEmpty()
            }
        }

        viewModel.loanActive.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onGetLoanActiveLoading()
                is VmData.Success -> view.onGetLoanActiveSuccess(it.data)
                is VmData.Failure -> view.onGetLoanActiveFailed(it.throwable)
                is VmData.Empty -> view.onGetLoanActiveEmpty()
            }
        }

        viewModel.isModalActive.observe(owner) {
            view.onGetModalActiveSuccess(it)
        }
    }
}
