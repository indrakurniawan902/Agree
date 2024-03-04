package com.agree.ecosystem.common.presentation.menu.notifications.detail

import com.agree.ecosystem.common.domain.reqres.model.notification.detail.DetailInbox

interface DetailNotificationContract {

    fun fetchNotificationDetail(inboxId: String)

    fun fetchNotificationDetailOnLoading()

    fun fetchNotificationDetailOnFailed(e: Throwable?) {
        // Do Nothing
    }

    fun fetchNotificationDetailOnSuccess(data: DetailInbox)
}
