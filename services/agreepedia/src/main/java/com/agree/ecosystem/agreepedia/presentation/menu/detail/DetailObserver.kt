package com.agree.ecosystem.agreepedia.presentation.menu.detail

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.devbase.data.source.VmData

class DetailObserver(
    private val view: DetailDataContract,
    private val viewModel: DetailViewModel
) : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        viewModel.articleDetail.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> {
                    view.onGetAgreepediaDetailLoading()
                }
                is VmData.Success -> {
                    if (it.data.isNotEmpty()) view.onGetAgreepediaDetailSuccess(it.data[0])
                    else view.onGetAgreepediaDetailEmpty()
                }
                is VmData.Failure -> view.onGetAgreepediaDetailFailed(it.throwable)
                is VmData.Empty -> view.onGetAgreepediaDetailEmpty()
            }
        }
    }
}
