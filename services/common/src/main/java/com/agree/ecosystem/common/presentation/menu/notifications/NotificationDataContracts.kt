package com.agree.ecosystem.common.presentation.menu.notifications

import com.agree.ecosystem.common.domain.reqres.model.notification.Inbox

interface NotificationDataContracts {

    fun fetchNotificationLists(userId: String?)

    fun notificationListsOnLoading()

    fun notificationListsOnSuccess(data: List<Inbox>)

    fun notificationListsOnFailed(e: Throwable?) {
        // Do Nothing
    }

    fun emptyNotificationLists()

    fun fetchLoadMoreNotificationLists(userId: String?)

    fun loadMoreNotificationListsOnLoading()

    fun loadMoreNotificationListsOnSuccess(data: List<Inbox>)

    fun loadMoreNotificationListsOnFailed()

    fun emptyLoadMoreNotificationLists()

    fun getNotificationBadge(userId: String?)
}
