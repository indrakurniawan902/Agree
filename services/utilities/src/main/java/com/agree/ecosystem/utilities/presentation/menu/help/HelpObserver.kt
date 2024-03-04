package com.agree.ecosystem.utilities.presentation.menu.help

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.agree.ecosystem.core.utils.utility.exception.EmptyException
import com.devbase.data.source.VmData

class HelpObserver(
    private val view: HelpDataContract,
    private val viewModel: HelpViewModel
) : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        viewModel.help.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.onGetHelpLoading()
                is VmData.Success -> view.onGetHelpSuccess(it.data)
                is VmData.Failure -> view.onGetHelpFailed(it.throwable)
                is VmData.Empty -> view.onGetHelpFailed(EmptyException())
            }
        }
    }
}
