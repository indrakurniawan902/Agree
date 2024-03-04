package com.agree.ecosystem.common.presentation.menu.notifications.detail

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.devbase.data.source.VmData

class DetailNotificationObserver(
    private val view: DetailNotificationContract,
    private val viewModel: DetailNotificationViewModel
) : DefaultLifecycleObserver {
    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)

        viewModel.data.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> {
                    view.fetchNotificationDetailOnLoading()
                }
                is VmData.Success -> {
                    view.fetchNotificationDetailOnSuccess(it.data)
                }
                is VmData.Failure -> Unit
                is VmData.Empty -> Unit
            }
        }
    }
}
