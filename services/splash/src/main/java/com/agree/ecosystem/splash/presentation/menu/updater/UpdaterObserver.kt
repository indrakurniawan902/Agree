package com.agree.ecosystem.splash.presentation.menu.updater

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.agree.ecosystem.core.utils.utility.exception.EmptyException
import com.devbase.data.source.VmData

class UpdaterObserver(
    private val view: UpdaterContract,
    private val viewModel: UpdaterViewModel
) : DefaultLifecycleObserver {

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        viewModel.appInfo.observe(owner) {
            when (it) {
                is VmData.Default -> view.onAppInfoIdle()
                is VmData.Loading -> view.onAppInfoLoading()
                is VmData.Success -> view.onAppInfoSuccess(it.data)
                is VmData.Failure -> view.onAppInfoFailed(it.throwable)
                is VmData.Empty -> view.onAppInfoFailed(EmptyException())
            }
        }
    }
}
