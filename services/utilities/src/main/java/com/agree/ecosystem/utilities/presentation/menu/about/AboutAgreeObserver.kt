package com.agree.ecosystem.utilities.presentation.menu.about

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.agree.ecosystem.core.utils.utility.exception.EmptyException
import com.devbase.data.source.VmData

class AboutAgreeObserver(
    private val view: AboutAgreeDataContract,
    private val viewModel: AboutAgreeViewModel
) : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        viewModel.aboutAgree.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onGetAboutAgreeLoading()
                is VmData.Success -> view.onGetAboutAgreeSuccess(it.data[0].about)
                is VmData.Failure -> view.onGetAboutAgreeFailed(it.throwable)
                is VmData.Empty -> view.onGetAboutAgreeFailed(EmptyException())
            }
        }
    }
}
