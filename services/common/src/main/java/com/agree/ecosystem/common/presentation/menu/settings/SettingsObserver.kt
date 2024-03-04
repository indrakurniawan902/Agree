package com.agree.ecosystem.common.presentation.menu.settings

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.agree.ecosystem.core.utils.utility.exception.EmptyException
import com.devbase.data.source.VmData

class SettingsObserver(
    private val view: SettingsDataContract,
    private val viewModel: SettingsViewModel
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
    }
}
