package com.agree.ecosystem.common.presentation.base.activity

import com.agree.ecosystem.common.domain.reqres.model.notification.UnreadInbox

interface NotificationCounterDataContract {
    fun getNotificationBadge(userId: String?)

    fun onNotificationBadgeSuccess(data: UnreadInbox)
}
