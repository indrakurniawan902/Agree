package com.agree.ecosystem.common.presentation.menu.notifications

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.devbase.data.source.VmData

class NotificationObserver(
    private val view: NotificationDataContracts,
    private val viewModel: NotificationViewModel
) : DefaultLifecycleObserver {

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        viewModel.lists.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.notificationListsOnLoading()
                is VmData.Success -> {
                    view.notificationListsOnSuccess(it.data)
                    viewModel.page++
                }
                is VmData.Failure -> view.notificationListsOnFailed(it.throwable)
                is VmData.Empty -> view.emptyNotificationLists()
            }
        }

        viewModel.loadMoreLists.observe(owner) {
            when (it) {
                is VmData.Default -> Unit
                is VmData.Loading -> view.loadMoreNotificationListsOnLoading()
                is VmData.Success -> {
                    view.loadMoreNotificationListsOnSuccess(it.data)
                    viewModel.page++
                }
                is VmData.Failure -> view.loadMoreNotificationListsOnFailed()
                is VmData.Empty -> view.emptyLoadMoreNotificationLists()
            }
        }
    }
}
